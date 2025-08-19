package com.soilapi.soilapi.overview.service;

import com.soilapi.soilapi.overview.dto.OverviewListFlatResponse;
import com.soilapi.soilapi.overview.dto.OverviewListRequest;
import com.soilapi.soilapi.overview.dto.OverviewListResponse;
import com.soilapi.soilapi.overview.dto.OverviewSelectIndiceListRequest;
import com.soilapi.soilapi.overview.dto.OverviewSelectIndiceListResponse;
import com.soilapi.soilapi.overview.dto.OverviewSelectWidgetListRequest;
import com.soilapi.soilapi.overview.dto.OverviewSelectWidgetListResponse;
import com.soilapi.soilapi.overview.dto.OvierviewUpdateIndiceRequest;
import com.soilapi.soilapi.overview.dto.OvierviewUpdateWidgetRequest;
import com.soilapi.soilapi.overview.util.OverviewProcedureBinder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OverviewService {

    @PersistenceContext
    private EntityManager eManager;

    public Page<OverviewListResponse> selectOverviewAdmin(OverviewListRequest dto) {
        try {
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_SelectOverviewAdmin");
            OverviewProcedureBinder.bindOverviewAdminParams(query, dto);
            query.execute();

            @SuppressWarnings("unchecked")
            List<Object[]> resultList = query.getResultList();
            List<OverviewListFlatResponse> responseList = resultList.stream().map(row -> {
                OverviewListFlatResponse response = new OverviewListFlatResponse();
                response.setRoleId(row[0] == null ? null : ((Number) row[0]).intValue());
                response.setRoleName((String) row[1]);
                response.setWidgetType((String) row[2]);
                response.setWidgetOrder(row[3] == null ? null : ((Number) row[3]).intValue());
                response.setCid(row[4] == null ? null : ((Number) row[4]).intValue());
                response.setCompId((String) row[5]);
                response.setCompName((String) row[6]);
                response.setCompNameKorean((String) row[7]);
                return response;
            }).collect(Collectors.toList());

            Map<Integer, OverviewListResponse> roleMap = new LinkedHashMap<>();

            for (OverviewListFlatResponse flat : responseList) {
                Integer roleId = flat.getRoleId();

                roleMap.computeIfAbsent(roleId, id -> {
                    OverviewListResponse response = new OverviewListResponse();
                    response.setRoleId(flat.getRoleId());
                    response.setRoleName(flat.getRoleName());
                    response.setWidgets(new ArrayList<>());
                    response.setIndices(new ArrayList<>());
                    return response;
                });
                if ("widget".equalsIgnoreCase(flat.getWidgetType())) {
                    OverviewListResponse.widgetInfo widget = new OverviewListResponse.widgetInfo();
                    widget.setWidgetOrder(flat.getWidgetOrder());
                    widget.setCid(flat.getCid());
                    widget.setCompId(flat.getCompId());
                    widget.setCompName(flat.getCompName());
                    widget.setCompNameKorean(flat.getCompNameKorean());
                    ((List<OverviewListResponse.widgetInfo>) roleMap.get(roleId).getWidgets()).add(widget);
                } else if ("indice".equalsIgnoreCase(flat.getWidgetType())) {
                    OverviewListResponse.widgetInfo indice = new OverviewListResponse.widgetInfo();
                    indice.setWidgetOrder(flat.getWidgetOrder());
                    indice.setCid(flat.getCid());
                    indice.setCompId(flat.getCompId());
                    indice.setCompName(flat.getCompName());
                    indice.setCompNameKorean(flat.getCompNameKorean());
                    ((List<OverviewListResponse.widgetInfo>) roleMap.get(roleId).getIndices()).add(indice);
                }

            }

            List<OverviewListResponse> responseMapList = new ArrayList<>(roleMap.values());
            System.out.println(responseMapList.get(0).getIndices().size());
            System.out.println(responseMapList.get(0).getWidgets().size());

            String baseQuery = "SELECT COUNT(DISTINCT RoleID) AS TotalCount FROM vw_OverviewDashboardAdmin WHERE 1=1 ";
            String searchType = dto.getSearchType();
            if (StringUtils.hasText(searchType)) {
                baseQuery += "AND " + searchType + " LIKE :searchKeyword";
            }
            Query countQuery = eManager.createNativeQuery(baseQuery);
            if (StringUtils.hasText(dto.getSearchKeyword())) {
                countQuery.setParameter("searchKeyword", "%" + dto.getSearchKeyword() + "%");
            }
            Long totalCount = ((Number) countQuery.getSingleResult()).longValue();
            Pageable pageable = PageRequest.of(dto.getPageNo() - 1, dto.getPageSize());
            return new PageImpl<>(responseMapList, pageable, totalCount);
        } catch (Exception e) {
            throw new RuntimeException("Overview Select 중 문제 발생.", e);
        }
    }

    public List<OverviewSelectIndiceListResponse> selectIndiceList(OverviewSelectIndiceListRequest dto){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_OverviewSelectIndiceList");
            OverviewProcedureBinder.bindOverviewSelectIndiceListParams(query, dto);
            query.execute();

            @SuppressWarnings("unchecked")
            List<Object[]> resultList = query.getResultList();
            List<OverviewSelectIndiceListResponse> responseList = resultList.stream().map(row -> {
                OverviewSelectIndiceListResponse response = new OverviewSelectIndiceListResponse();
                response.setCid(((Number) row[0]).intValue());
                response.setCompId((String) row[1]);
                response.setCompType((String) row[2]);
                response.setNameKor((String) row[3]);
                response.setNameEng((String) row[4]);
                return response;
            }).collect(Collectors.toList());

            return responseList;
        } catch (Exception e) {
            throw new RuntimeException("Overview SelectIndiceList 중 문제 발생.", e);
        }
    }

    public List<OverviewSelectWidgetListResponse> selectWidgetList(OverviewSelectWidgetListRequest dto){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_OverviewSelectWidgetList");
            OverviewProcedureBinder.bindOverviewSelectWidgetListParams(query, dto);
            query.execute();

            @SuppressWarnings("unchecked")
            List<Object[]> resultList = query.getResultList();
            List<OverviewSelectWidgetListResponse> responseList = resultList.stream().map(row -> {
                OverviewSelectWidgetListResponse response = new OverviewSelectWidgetListResponse();
                response.setCid(((Number) row[0]).intValue());
                response.setCompId((String) row[1]);
                response.setCompType((String) row[2]);
                response.setNameKor((String) row[3]);
                response.setNameEng((String) row[4]);
                return response;
            }).collect(Collectors.toList());

            return responseList;
        } catch (Exception e) {
            throw new RuntimeException("Overview SelectWidgetList 중 문제 발생.", e);
        }
    }

    public void updateWidget(OvierviewUpdateWidgetRequest dto){
        try{
            System.out.println(dto);
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_OverviewUpdateWidget");
            OverviewProcedureBinder.bindOverviewUpdateWidgetParams(query, dto);
            query.execute();
        } catch (Exception e) {
            throw new RuntimeException("Overview UpdateWidget 중 문제 발생.", e);
        }
    }

    public void updateIndice(OvierviewUpdateIndiceRequest dto){
        try{
            System.out.println(dto);
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_OverviewUpdateIndice");
            OverviewProcedureBinder.bindOverviewUpdateIndiceParams(query, dto);
            query.execute();
        } catch (Exception e) {
            throw new RuntimeException("Overview Update Indice 중 문제 발생.", e);
        }
    }
}