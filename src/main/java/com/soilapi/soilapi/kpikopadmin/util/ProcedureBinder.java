package com.soilapi.soilapi.kpikopadmin.util;

import com.soilapi.soilapi.kpikopadmin.dto.KpiKopAdminInsertRequest;
import com.soilapi.soilapi.kpikopadmin.dto.KpiKopAdminSelectRequest;
import com.soilapi.soilapi.kpikopadmin.dto.KpiKopAdminUpdateRequest;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

public class ProcedureBinder {
    public static void bindKpiKopAdminInsertParams(StoredProcedureQuery query, KpiKopAdminInsertRequest dto) {
        query.registerStoredProcedureParameter("CompID", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("CompType", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("UOM", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("UOMKorean", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("ETLJobName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Name_KOR", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Name_ENG", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("WidgetObjectId", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("OverviewWidgetId", String.class, ParameterMode.IN);

        query.setParameter("CompID", dto.getCompId());
        query.setParameter("CompType", dto.getCompType());
        query.setParameter("UOM", dto.getUom());
        query.setParameter("UOMKorean", dto.getUomKorean());
        query.setParameter("ETLJobName", dto.getEtlJobName());
        query.setParameter("Name_KOR", dto.getNameKor());
        query.setParameter("Name_ENG", dto.getNameEng());
        query.setParameter("WidgetObjectId", dto.getWidgetObjectId());
        query.setParameter("OverviewWidgetId", dto.getOverviewWidgetId());
    }
    public static void bindKpiKopAdminUpdateParams(StoredProcedureQuery query, KpiKopAdminUpdateRequest dto) {
        query.registerStoredProcedureParameter("CID", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("CompID", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("CompType", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("UOM", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("UOMKorean", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("ETLJobName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Name_KOR", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Name_ENG", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("WidgetObjectId", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("OverviewWidgetId", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("UpdatedBy", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("isActive", String.class, ParameterMode.IN);

        query.setParameter("CID", dto.getCid());
        query.setParameter("CompID", dto.getCompId());
        query.setParameter("CompType", dto.getCompType());
        query.setParameter("UOM", dto.getUom());
        query.setParameter("UOMKorean", dto.getUomKorean());
        query.setParameter("ETLJobName", dto.getEtlJobName());
        query.setParameter("Name_KOR", dto.getNameKor());
        query.setParameter("Name_ENG", dto.getNameEng());
        query.setParameter("WidgetObjectId", dto.getWidgetObjectId());
        query.setParameter("OverviewWidgetId", dto.getOverviewWidgetId());
        query.setParameter("UpdatedBy", dto.getUpdatedBy());
        query.setParameter("isActive", dto.getIsActive());
    }
    public static void binKpiKopAdminSelectParams(StoredProcedureQuery query, KpiKopAdminSelectRequest dto){
        query.registerStoredProcedureParameter("pageNo", int.class, ParameterMode.IN)
            .registerStoredProcedureParameter("pageSize", int.class, ParameterMode.IN)
            .registerStoredProcedureParameter("orderBy", String.class, ParameterMode.IN)
            .registerStoredProcedureParameter("searchType", String.class, ParameterMode.IN)
            .registerStoredProcedureParameter("searchKeyword", String.class, ParameterMode.IN)
            .registerStoredProcedureParameter("isActive", String.class, ParameterMode.IN);
             
        query.setParameter("pageNo", dto.getPageNo())
            .setParameter("pageSize", dto.getPageSize())
            .setParameter("orderBy", dto.getOrderBy())
            .setParameter("searchType", dto.getSearchType())
            .setParameter("searchKeyword", dto.getSearchKeyword())
            .setParameter("isActive", dto.getIsActive());     
    }
}
