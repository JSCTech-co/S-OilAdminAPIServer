package com.soilapi.soilapi.roleadmin.util;

import com.soilapi.soilapi.roleadmin.dto.RoleAdminListRequest;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

public class RoleAdminProcedureBinder {
    public static void bindRoleAdminParams(StoredProcedureQuery query, RoleAdminListRequest dto) {
        query.registerStoredProcedureParameter("pageNo", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("pageSize", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("orderBy", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("searchType", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("searchKeyword", String.class, ParameterMode.IN);

        query.setParameter("pageNo", dto.getPageNo());
        query.setParameter("pageSize", dto.getPageSize());
        query.setParameter("orderBy", dto.getOrderBy());
        query.setParameter("searchType", dto.getSearchType());
        query.setParameter("searchKeyword", dto.getSearchKeyword());
    }
}
