package com.soilapi.soilapi.kpikopfilteradmin.util;

import com.soilapi.soilapi.kpikopfilteradmin.dto.KpiKopFilterAdminInsertRequest;
import com.soilapi.soilapi.kpikopfilteradmin.dto.KpiKopFilterAdminSelectRequest;
import com.soilapi.soilapi.kpikopfilteradmin.dto.KpiKopFilterAdminUpdateRequest;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

public class KpiKopFilterAdminProcedureBinder {
    
    public static void bindKpiKopFilterAdminParams(StoredProcedureQuery query, KpiKopFilterAdminSelectRequest dto){
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

    public static void bindKpiKopFilterAdminInsertParams(StoredProcedureQuery query, KpiKopFilterAdminInsertRequest dto){
        query.registerStoredProcedureParameter("CompID", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FilterType", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FilterName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FieldName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FilterLabel", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("WidgetObjectId", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FilterObjId", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FilterQlikId", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FilterSequence", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("PageName", String.class, ParameterMode.IN);

        query.setParameter("CompID", dto.getCompId());
        query.setParameter("FilterType", dto.getFilterType());
        query.setParameter("FilterName", dto.getFilterName());
        query.setParameter("FieldName", dto.getFieldName());
        query.setParameter("FilterLabel", dto.getFilterLabel());
        query.setParameter("WidgetObjectId", dto.getWidgetObjectId());
        query.setParameter("FilterObjId", dto.getFilterObjId());
        query.setParameter("FilterQlikId", dto.getFilterQlikId());
        query.setParameter("FilterSequence", dto.getFilterSequence());
        query.setParameter("PageName", dto.getPageName());
    }

    public static void bindKpiKopFilterAdminUpdateParams(StoredProcedureQuery query, KpiKopFilterAdminUpdateRequest dto){
        query.registerStoredProcedureParameter("FilterID", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FilterType", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FilterName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FieldName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FilterLabel", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("WidgetObjectId", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FilterObjId", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FilterQlikId", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FilterSequence", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("PageName", String.class, ParameterMode.IN);

        query.setParameter("FilterID", dto.getFilterId());
        query.setParameter("FilterType", dto.getFilterType());
        query.setParameter("FilterName", dto.getFilterName());
        query.setParameter("FieldName", dto.getFieldName());
        query.setParameter("FilterLabel", dto.getFilterLabel());
        query.setParameter("WidgetObjectId", dto.getWidgetObjectId());
        query.setParameter("FilterObjId", dto.getFilterObjId());
        query.setParameter("FilterQlikId", dto.getFilterQlikId());
        query.setParameter("FilterSequence", dto.getFilterSequence());
        query.setParameter("PageName", dto.getPageName());
    }

    public static void bindKpiKopFilterAdminDeleteParams(StoredProcedureQuery query, int filterId){
        query.registerStoredProcedureParameter("FilterId", Integer.class, ParameterMode.IN);
        query.setParameter("FilterId", filterId);
    }
}
