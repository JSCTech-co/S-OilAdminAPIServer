package com.soilapi.soilapi.comprolemapping.util;

import java.util.List;
import java.util.stream.Collectors;

import com.soilapi.soilapi.comprolemapping.dto.CompToRoleListRequest;
import com.soilapi.soilapi.comprolemapping.dto.CompToRoleModifyRequest;
import com.soilapi.soilapi.comprolemapping.dto.RoleToCompListRequest;
import com.soilapi.soilapi.comprolemapping.dto.RoleToCompModifyRequest;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

public class CompRoleDetailsProcedureBinder {

    public static void bindRoleToCompListParams(StoredProcedureQuery query, RoleToCompListRequest dto){
        query.registerStoredProcedureParameter("pageNo", int.class, ParameterMode.IN)
             .registerStoredProcedureParameter("pageSize", int.class, ParameterMode.IN)
             .registerStoredProcedureParameter("orderBy", String.class, ParameterMode.IN)
             .registerStoredProcedureParameter("searchType", String.class, ParameterMode.IN)
             .registerStoredProcedureParameter("searchKeyword", String.class, ParameterMode.IN);
        
        query.setParameter("pageNo", dto.getPageNo());
        query.setParameter("pageSize", dto.getPageSize());
        query.setParameter("orderBy", dto.getOrderBy());
        query.setParameter("searchType", dto.getSearchType());
        query.setParameter("searchKeyword", dto.getSearchKeyword());
    }

    public static void bindRoleToCompMappingListParams(StoredProcedureQuery query, int roleId){
        query.registerStoredProcedureParameter("roleId", int.class, ParameterMode.IN);
        
        query.setParameter("roleId", roleId);
    }

    public static void bindRoleToCompModifyParams(StoredProcedureQuery query, RoleToCompModifyRequest dto){
        String addedKPI = null;
        String addedKOP = null;
        String addedReport = null;

        String removedKPI = null;
        String removedKOP = null;
        String removedReport = null;
  
        addedKPI = String.join(",", dto.getAddedList().getOrDefault("KPI", List.of()));
        addedKOP = String.join(",", dto.getAddedList().getOrDefault("KOP", List.of()));
        addedReport = String.join(",", dto.getAddedList().getOrDefault("Report", List.of()));

        removedKPI = String.join(",", dto.getRemovedList().getOrDefault("KPI", List.of()));
        removedKOP = String.join(",", dto.getRemovedList().getOrDefault("KOP", List.of()));
        removedReport = String.join(",", dto.getRemovedList().getOrDefault("Report", List.of()));

        query.registerStoredProcedureParameter("roleId", int.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("addedKPI", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("addedKOP", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("addedReport", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("removedKPI", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("removedKOP", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("removedReport", String.class, ParameterMode.IN);

        query.setParameter("roleId", dto.getRoleId());
        query.setParameter("addedKPI", addedKPI);
        query.setParameter("addedKOP", addedKOP);
        query.setParameter("addedReport", addedReport);
        query.setParameter("removedKPI", removedKPI);
        query.setParameter("removedKOP", removedKOP);
        query.setParameter("removedReport", removedReport);


    }

    public static void bindCompToRoleListParams(StoredProcedureQuery query, CompToRoleListRequest dto){
        query.registerStoredProcedureParameter("pageNo", int.class, ParameterMode.IN)
             .registerStoredProcedureParameter("pageSize", int.class, ParameterMode.IN)
             .registerStoredProcedureParameter("orderBy", String.class, ParameterMode.IN)
             .registerStoredProcedureParameter("searchType", String.class, ParameterMode.IN)
             .registerStoredProcedureParameter("searchKeyword", String.class, ParameterMode.IN);
        
        query.setParameter("pageNo", dto.getPageNo());
        query.setParameter("pageSize", dto.getPageSize());
        query.setParameter("orderBy", dto.getOrderBy());
        query.setParameter("searchType", dto.getSearchType());
        query.setParameter("searchKeyword", dto.getSearchKeyword());
    }

    public static void bindCompToRoleMappingListParams(StoredProcedureQuery query, String compId){
        query.registerStoredProcedureParameter("compId", String.class, ParameterMode.IN);
        
        query.setParameter("compId", compId);
    }

    public static void bindCompToRoleModifyParams(StoredProcedureQuery query, CompToRoleModifyRequest dto){
        String addedRole = (dto.getAddedRole() != null && !dto.getAddedRole().isEmpty())
        ? dto.getAddedRole().stream()
            .map(String::valueOf)
            .collect(Collectors.joining(","))
        : null;

        String removedRole = (dto.getRemovedRole() != null && !dto.getRemovedRole().isEmpty())
            ? dto.getRemovedRole().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","))
            : null;

        query
            .registerStoredProcedureParameter("CompId", String.class, ParameterMode.IN)
            .registerStoredProcedureParameter("CompType", String.class, ParameterMode.IN)
            .registerStoredProcedureParameter("AddedRole", String.class, ParameterMode.IN)
            .registerStoredProcedureParameter("RemovedRole", String.class, ParameterMode.IN);

        query
            .setParameter("CompId", dto.getCompId())
            .setParameter("CompType", dto.getCompType())
            .setParameter("AddedRole", addedRole)
            .setParameter("RemovedRole", removedRole);
    }

}
