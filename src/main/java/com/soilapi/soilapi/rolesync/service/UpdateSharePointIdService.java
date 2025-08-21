package com.soilapi.soilapi.rolesync.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.soilapi.soilapi.rolesync.dto.RoleItem;
import com.soilapi.soilapi.rolesync.dto.UpdateResult;
import com.soilapi.soilapi.rolesync.util.XmlToJsonRoleParser;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UpdateSharePointIdService {
    private final NamedParameterJdbcTemplate jdbc;

    @Transactional
    public UpdateResult updateFromList(List<RoleItem> items, boolean dryRun, boolean ignoreCase, boolean insertMissing) {
        int requested = (items == null) ? 0 : items.size();
        UpdateResult result = new UpdateResult();
        result.setRequestedCount(requested);

        if (requested == 0) {
            result.setUniqueRoleCount(0);
            result.setUpdatedRows(0);
            result.setNotMatchedRoles(Collections.emptyList());
            result.setInsertedRows(0);
            result.setInsertedRoles(Collections.emptyList());
            return result;
        }

        // 동일 roleName 여러 번 오는 경우 마지막 SPID로 덮어쓰기
        Map<String, Integer> latestByRole = new LinkedHashMap<>();
        for (RoleItem it : items) {
            if (it == null) continue;
            String roleName = (it.getRoleName() == null) ? "" : it.getRoleName().trim();
            if (roleName.isEmpty()) continue;
            latestByRole.put(roleName, it.getSpid());
        }

        List<String> roleNames = new ArrayList<>(latestByRole.keySet());
        result.setUniqueRoleCount(roleNames.size());

        // 존재 여부 확인
        List<String> notMatched;
        if (ignoreCase) {
            List<String> existing = jdbc.query("SELECT RoleName FROM DSVRoleMaster",
                    rs -> {
                        List<String> list = new ArrayList<>();
                        while (rs.next()) list.add(rs.getString(1));
                        return list;
                    });
            Set<String> existingUpper = existing.stream()
                    .filter(Objects::nonNull)
                    .map(s -> s.trim().toUpperCase())
                    .collect(Collectors.toSet());
            notMatched = roleNames.stream()
                    .filter(rn -> !existingUpper.contains(rn.toUpperCase()))
                    .collect(Collectors.toList());
        } else {
            MapSqlParameterSource namesPs = new MapSqlParameterSource("names", roleNames);
            List<String> existing = jdbc.query(
                    "SELECT RoleName FROM DSVRoleMaster WHERE RoleName IN (:names)",
                    namesPs,
                    (rs, i) -> rs.getString(1)
            );
            Set<String> existingSet = existing.stream()
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .collect(Collectors.toSet());
            notMatched = roleNames.stream()
                    .filter(rn -> !existingSet.contains(rn))
                    .collect(Collectors.toList());
        }

        result.setNotMatchedRoles(notMatched);

        if (dryRun) {
            result.setUpdatedRows(0);
            result.setInsertedRows(0);
            result.setInsertedRoles(Collections.emptyList());
            return result;
        }

        // 1단계: UPDATE 수행
        int updated = 0;
        if (ignoreCase) {
            String sql = """
                UPDATE DSVRoleMaster
                   SET sharepointId = :spid
                 WHERE UPPER(RTRIM(LTRIM(RoleName))) = :roleNameNorm
                   AND (sharepointId IS NULL OR sharepointId <> :spid)
                """;
            for (Map.Entry<String, Integer> e : latestByRole.entrySet()) {
                MapSqlParameterSource ps = new MapSqlParameterSource()
                        .addValue("spid", e.getValue())
                        .addValue("roleNameNorm", e.getKey().trim().toUpperCase());
                updated += jdbc.update(sql, ps);
            }
        } else {
            String sql = """
                UPDATE DSVRoleMaster
                   SET sharepointId = :spid
                 WHERE RoleName = :roleName
                   AND (sharepointId IS NULL OR sharepointId <> :spid)
                """;
            List<MapSqlParameterSource> batch = new ArrayList<>();
            for (Map.Entry<String, Integer> e : latestByRole.entrySet()) {
                batch.add(new MapSqlParameterSource()
                        .addValue("spid", e.getValue())
                        .addValue("roleName", e.getKey()));
            }
            if (!batch.isEmpty()) {
                int[] counts = jdbc.batchUpdate(sql, batch.toArray(new MapSqlParameterSource[0]));
                for (int c : counts) updated += c;
            }
        }
        result.setUpdatedRows(updated);

        // 2단계: 매칭 실패한 항목들을 INSERT (옵션)
        if (insertMissing && !notMatched.isEmpty()) {
            List<String> insertedRoles = new ArrayList<>();
            int inserted = 0;

            if (ignoreCase) {
                // 대소문자 무시 비교를 위한 NOT EXISTS 조건
                String insertSql = """
                    INSERT INTO DSVRoleMaster (RoleName, sharepointId)
                    SELECT :roleName, :spid
                    WHERE NOT EXISTS (
                        SELECT 1 FROM DSVRoleMaster
                         WHERE UPPER(RTRIM(LTRIM(RoleName))) = :roleNameNorm
                    )
                    """;
                for (String rn : notMatched) {
                    Integer spid = latestByRole.get(rn);
                    MapSqlParameterSource ps = new MapSqlParameterSource()
                            .addValue("roleName", rn)
                            .addValue("spid", spid)
                            .addValue("roleNameNorm", rn.trim().toUpperCase());
                    int c = jdbc.update(insertSql, ps);
                    if (c > 0) {
                        inserted += c;
                        insertedRoles.add(rn);
                    }
                }
            } else {
                // 정확 일치 비교를 위한 NOT EXISTS 조건
                String insertSql = """
                    INSERT INTO DSVRoleMaster (RoleName, sharepointId)
                    SELECT :roleName, :spid
                    WHERE NOT EXISTS (
                        SELECT 1 FROM DSVRoleMaster WHERE RoleName = :roleName
                    )
                    """;
                for (String rn : notMatched) {
                    Integer spid = latestByRole.get(rn);
                    MapSqlParameterSource ps = new MapSqlParameterSource()
                            .addValue("roleName", rn)
                            .addValue("spid", spid);
                    int c = jdbc.update(insertSql, ps);
                    if (c > 0) {
                        inserted += c;
                        insertedRoles.add(rn);
                    }
                }
            }
            result.setInsertedRows(inserted);
            result.setInsertedRoles(insertedRoles);
        } else {
            result.setInsertedRows(0);
            result.setInsertedRoles(Collections.emptyList());
        }

        return result;
    }

    public void xmlparse(){
        try{
            XmlToJsonRoleParser.exportToJson();
        }catch(Exception e){
            throw new RuntimeException("xmlparse 중 문제 발생.", e);
        }
        
    }

}
