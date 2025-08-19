package com.soilapi.soilapi.overview.util;

import com.soilapi.soilapi.overview.dto.OverviewListRequest;
import com.soilapi.soilapi.overview.dto.OverviewSelectIndiceListRequest;
import com.soilapi.soilapi.overview.dto.OverviewSelectWidgetListRequest;
import com.soilapi.soilapi.overview.dto.OvierviewUpdateIndiceRequest;
import com.soilapi.soilapi.overview.dto.OvierviewUpdateWidgetRequest;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

public class OverviewProcedureBinder {

    public static void bindOverviewAdminParams(StoredProcedureQuery query, OverviewListRequest dto) {
        query.registerStoredProcedureParameter("pageNo", Integer.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("pageSize", Integer.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("orderBy", String.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("searchType", String.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("searchKeyword", String.class, jakarta.persistence.ParameterMode.IN);

        query.setParameter("pageNo", dto.getPageNo());
        query.setParameter("pageSize", dto.getPageSize());
        query.setParameter("orderBy", dto.getOrderBy());
        query.setParameter("searchType", dto.getSearchType());
        query.setParameter("searchKeyword", dto.getSearchKeyword());
    }

    public static void bindOverviewSelectIndiceListParams(StoredProcedureQuery query, OverviewSelectIndiceListRequest dto){
        query.registerStoredProcedureParameter("roleId", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("orderBy", String.class, ParameterMode.IN);

        query.setParameter("roleId", dto.getRoleId());
        query.setParameter("orderBy", dto.getOrderBy());
    }

    public static void bindOverviewSelectWidgetListParams(StoredProcedureQuery query, OverviewSelectWidgetListRequest dto){
        query.registerStoredProcedureParameter("roleId", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("orderBy", String.class, ParameterMode.IN);

        query.setParameter("roleId", dto.getRoleId());
        query.setParameter("orderBy", dto.getOrderBy());
    }

    public static void bindOverviewUpdateWidgetParams(StoredProcedureQuery query, OvierviewUpdateWidgetRequest dto){
        query.registerStoredProcedureParameter("roleId", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("cid", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("widgetOrder", Integer.class, ParameterMode.IN);

        query.setParameter("roleId", dto.getRoleId());
        query.setParameter("cid", dto.getCid());
        query.setParameter("widgetOrder", dto.getWidgetOrder());
    }

    public static void bindOverviewUpdateIndiceParams(StoredProcedureQuery query, OvierviewUpdateIndiceRequest dto){
        query.registerStoredProcedureParameter("roleId", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("cid", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("widgetOrder", Integer.class, ParameterMode.IN);

        query.setParameter("roleId", dto.getRoleId());
        query.setParameter("cid", dto.getCid());
        query.setParameter("widgetOrder", dto.getWidgetOrder());
    }
}