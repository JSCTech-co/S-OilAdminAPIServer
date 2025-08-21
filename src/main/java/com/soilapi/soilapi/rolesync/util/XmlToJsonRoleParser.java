package com.soilapi.soilapi.rolesync.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class XmlToJsonRoleParser {
    private static final String INPUT_XML  = "C:\\Users\\JiHwon-Rim\\Downloads\\permissiongroup1.xml";   // 수정하여 사용
    private static final String OUTPUT_JSON = "C:\\Users\\JiHwon-Rim\\Downloads\\permissiongroup1.json"; // 수정하여 사용

    // 결과 DTO
    public static class RoleItem {
        public int spid;        // d:Id 값
        public String roleName; // d:Title 값
        public RoleItem(int spid, String roleName) { this.spid = spid; this.roleName = roleName; }
        @Override public boolean equals(Object o){ if(this==o)return true; if(!(o instanceof RoleItem))return false; RoleItem r=(RoleItem)o; return spid==r.spid && Objects.equals(roleName,r.roleName);}
        @Override public int hashCode(){ return Objects.hash(spid, roleName); }
    }

    // 네임스페이스 컨텍스트 - Atom, OData 네임스페이스 정의
    private static class AtomNamespaceContext implements NamespaceContext {
        @Override public String getNamespaceURI(String prefix){
            if("atom".equals(prefix)) return "http://www.w3.org/2005/Atom";
            if("d".equals(prefix))    return "http://schemas.microsoft.com/ado/2007/08/dataservices";
            if("m".equals(prefix))    return "http://schemas.microsoft.com/ado/2007/08/dataservices/metadata";
            if("xml".equals(prefix))  return XMLConstants.XML_NS_URI;
            return XMLConstants.NULL_NS_URI;
        }
        @Override public String getPrefix(String namespaceURI){ return null; }
        @Override public Iterator<String> getPrefixes(String namespaceURI){ return null; }
    }

     // XML 파일 파싱 - 표준 파싱 실패 시 관대한 파싱으로 재시도
    private static List<RoleItem> parseXmlToItems(File xmlFile) throws Exception {
        String raw = readAll(xmlFile);
        Document doc = tryParseLenient(raw);

        XPathFactory xpf = XPathFactory.newInstance();
        XPath xp = xpf.newXPath();
        xp.setNamespaceContext(new AtomNamespaceContext());

        // 1차 - link title="Member" 아래 inline 엔트리
        NodeList propsInline = (NodeList) xp.evaluate(
            "//atom:entry/atom:link[@title='Member']/m:inline/atom:entry/atom:content/m:properties",
            doc, XPathConstants.NODESET
        );

        // 2차 - 엔트리 바로 아래 content > properties
        NodeList propsDirect = (NodeList) xp.evaluate(
            "//atom:entry/atom:content/m:properties",
            doc, XPathConstants.NODESET
        );

        List<Node> propertiesNodes = new ArrayList<>();
        addAll(propertiesNodes, propsInline);
        addAll(propertiesNodes, propsDirect);

        Set<RoleItem> result = new LinkedHashSet<>();
        for (Node props : propertiesNodes) {
            String idText = textOrNull(xp, "d:Id/text()", props);
            String title  = textOrNull(xp, "d:Title/text()", props);
            if (idText == null || title == null) continue;
            idText = idText.trim();
            title  = title.trim();
            if (idText.isEmpty() || title.isEmpty()) continue;
            try {
                int spid = Integer.parseInt(idText);
                result.add(new RoleItem(spid, title));
            } catch (NumberFormatException ignore) {
                // d:Id가 정수가 아니면 스킵
            }
        }
        return new ArrayList<>(result);
    }

    // 표준 파싱 후 실패 시 관대한 파싱 - XML 선언 제거 후 ROOT로 감싸 재파싱
    private static Document tryParseLenient(String raw) throws Exception {
        try { return parseStrict(raw); }
        catch (Exception first) {
            String withoutDecl = raw.replaceAll("<\\?xml[^>]*\\?>", "");
            String wrapped = "<ROOT>" + withoutDecl + "</ROOT>";
            return parseStrict(wrapped);
        }
    }

    // 보안 옵션을 포함한 DOM 파서
    private static Document parseStrict(String xml) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        // 보안 설정 - XXE 방지
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        try { dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true); } catch (Exception ignore) {}
        try { dbf.setFeature("http://xml.org/sax/features/external-general-entities", false); } catch (Exception ignore) {}
        try { dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false); } catch (Exception ignore) {}
        dbf.setXIncludeAware(false);
        dbf.setExpandEntityReferences(false);

        DocumentBuilder db = dbf.newDocumentBuilder();
        try (ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))) {
            return db.parse(in);
        }
    }

    // JSON 파일로 저장
    private static void writeAsJson(List<RoleItem> items, File out) throws IOException {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(out, items);
    }

    private static String textOrNull(XPath xp, String expr, Object ctx) throws XPathExpressionException {
        String s = (String) xp.evaluate(expr, ctx, XPathConstants.STRING);
        return (s != null && !s.isEmpty()) ? s : null;
    }
    private static void addAll(List<Node> list, NodeList nl){
        if(nl==null) return; for(int i=0;i<nl.getLength();i++) list.add(nl.item(i));
    }
    private static String readAll(File f) throws IOException {
        try (InputStream is = new FileInputStream(f)) { return new String(is.readAllBytes(), StandardCharsets.UTF_8); }
    }

    public static void exportToJson() throws Exception {
        List<RoleItem> items = parseXmlToItems(new File(INPUT_XML));
        items = items.stream()
                .sorted(Comparator.comparingInt((RoleItem r) -> r.spid).thenComparing(r -> r.roleName))
                .collect(Collectors.toList());
        writeAsJson(items, new File(OUTPUT_JSON));
    }
    
}
