package com.soilapi.soilapi.indiceadmin.util;


import com.soilapi.soilapi.indiceadmin.dto.IndiceAdminSelectRequest;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

public class IndiceAdminProcedureBinder {
    public static void bindIndiceAdminSelectParams(StoredProcedureQuery query, IndiceAdminSelectRequest dto){
        query.registerStoredProcedureParameter("pageNo", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("pageSize", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("searchType", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("searchKeyword", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("orderBy", String.class, ParameterMode.IN);

        query.setParameter("pageNo", dto.getPageNo());
        query.setParameter("pageSize", dto.getPageSize());
        query.setParameter("searchType", dto.getSearchType());
        query.setParameter("searchKeyword", dto.getSearchKeyword());
        query.setParameter("orderBy", dto.getOrderBy());
    }
}
