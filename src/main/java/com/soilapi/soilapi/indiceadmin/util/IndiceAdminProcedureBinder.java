package com.soilapi.soilapi.indiceadmin.util;


import com.soilapi.soilapi.indiceadmin.dto.IndiceAdminInsertRequest;
import com.soilapi.soilapi.indiceadmin.dto.IndiceAdminSelectRequest;
import com.soilapi.soilapi.indiceadmin.dto.IndiceAdminUpdateRequest;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

public class IndiceAdminProcedureBinder {
    public static void bindIndiceAdminSelectParams(StoredProcedureQuery query, IndiceAdminSelectRequest dto){
        query.registerStoredProcedureParameter("pageNo", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("pageSize", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("orderBy", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("searchType", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("searchKeyword", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("isActive", String.class, ParameterMode.IN);

        query.setParameter("pageNo", dto.getPageNo());
        query.setParameter("pageSize", dto.getPageSize());
        query.setParameter("orderBy", dto.getOrderBy());
        query.setParameter("searchType", dto.getSearchType());
        query.setParameter("searchKeyword", dto.getSearchKeyword());
        query.setParameter("isActive", dto.getIsActive());
    }

    public static void bindIndiceAdminInsertParams(StoredProcedureQuery query, IndiceAdminInsertRequest dto){
        query.registerStoredProcedureParameter("compId", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("compType", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("nameKor", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("nameEng", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("currentMonth", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("flag", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("secondaryValue", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("secondaryName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("uom", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("uomKorean", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("etlJobName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("isActive", String.class, ParameterMode.IN);

        query.setParameter("compId", dto.getCompId());
        query.setParameter("compType", dto.getCompType());
        query.setParameter("nameKor", dto.getNameKor());
        query.setParameter("nameEng", dto.getNameEng());
        query.setParameter("currentMonth", dto.getCurrentMonth());
        query.setParameter("flag", dto.getFlag());
        query.setParameter("secondaryValue", dto.getSecondaryValue());
        query.setParameter("secondaryName", dto.getSecondaryName());
        query.setParameter("uom", dto.getUom());
        query.setParameter("uomKorean", dto.getUomKorean());
        query.setParameter("etlJobName", dto.getEtlJobName());
        query.setParameter("isActive", dto.getIsActive());    
    }

    public static void bindIndiceAdminUpdateParams(StoredProcedureQuery query, IndiceAdminUpdateRequest dto){
        query.registerStoredProcedureParameter("compId", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("compType", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("nameKor", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("nameEng", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("currentMonth", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("flag", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("secondaryValue", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("secondaryName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("uom", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("uomKorean", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("etlJobName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("isActive", String.class, ParameterMode.IN);

        query.setParameter("compId", dto.getCompId());
        query.setParameter("compType", dto.getCompType());
        query.setParameter("nameKor", dto.getNameKor());
        query.setParameter("nameEng", dto.getNameEng());
        query.setParameter("currentMonth", dto.getCurrentMonth());
        query.setParameter("flag", dto.getFlag());
        query.setParameter("secondaryValue", dto.getSecondaryValue());
        query.setParameter("secondaryName", dto.getSecondaryName());
        query.setParameter("uom", dto.getUom());
        query.setParameter("uomKorean", dto.getUomKorean());
        query.setParameter("etlJobName", dto.getEtlJobName());
        query.setParameter("isActive", dto.getIsActive());    
    }
}
