package com.soilapi.soilapi.appmapping.util;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.soilapi.soilapi.appmapping.dto.AppMasterInsertRequest;
import com.soilapi.soilapi.appmapping.dto.AppMasterListRequest;
import com.soilapi.soilapi.appmapping.dto.AppMasterUpdateRequest;
import com.soilapi.soilapi.appmapping.dto.AppToCompModifyRequest;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

public class AppMappingProcedureBinder {    
    public static void bindAppMasterListParams(StoredProcedureQuery query, AppMasterListRequest dto){
        query.registerStoredProcedureParameter("pageNo", int.class, ParameterMode.IN)
            .registerStoredProcedureParameter("pageSize", int.class, ParameterMode.IN)
            .registerStoredProcedureParameter("orderBy", String.class, ParameterMode.IN)
            .registerStoredProcedureParameter("searchType", String.class, ParameterMode.IN)
            .registerStoredProcedureParameter("searchKeyword", String.class, ParameterMode.IN);
             
        query.setParameter("pageNo", dto.getPageNo())
            .setParameter("pageSize", dto.getPageSize())
            .setParameter("orderBy", dto.getOrderBy())
            .setParameter("searchType", dto.getSearchType())
            .setParameter("searchKeyword", dto.getSearchKeyword()); 
    }

    public static void bindAppMasterInsertParams(StoredProcedureQuery query, AppMasterInsertRequest dto){
        query.registerStoredProcedureParameter("appName", String.class, ParameterMode.IN)
        .registerStoredProcedureParameter("appId", String.class, ParameterMode.IN)
        .registerStoredProcedureParameter("appType", String.class, ParameterMode.IN);

        query.setParameter("appName", dto.getAppName())
        .setParameter("appId", dto.getAppId())
        .setParameter("appType", dto.getAppType());
    }

    public static void bindAppMasterUpdateParams(StoredProcedureQuery query, AppMasterUpdateRequest dto){
        query.registerStoredProcedureParameter("aid", int.class, ParameterMode.IN)
        .registerStoredProcedureParameter("appName", String.class, ParameterMode.IN)
        .registerStoredProcedureParameter("appId", String.class, ParameterMode.IN)
        .registerStoredProcedureParameter("appType", String.class, ParameterMode.IN);

        query.setParameter("aid", dto.getAid())
        .setParameter("appName", dto.getAppName())
        .setParameter("appId", dto.getAppId())
        .setParameter("appType", dto.getAppType());
    }

    public static void bindAppToCompMappingListParams(StoredProcedureQuery query, int aid){
        query.registerStoredProcedureParameter("aid", int.class, ParameterMode.IN);

        query.setParameter("aid", aid);
    }

    public static void bindAppToCompMappingModifyParams(StoredProcedureQuery query, AppToCompModifyRequest dto){
        String addList = (dto.getAddList() != null && !dto.getAddList().isEmpty())?
        dto.getAddList().stream().map(String::valueOf).collect(Collectors.joining(",")):null;
        String removeList  = (dto.getRemoveList() != null && !dto.getRemoveList().isEmpty())?
        dto.getRemoveList().stream().map(String::valueOf).collect(Collectors.joining(",")):null;
        
        
        query.registerStoredProcedureParameter("aid", int.class, ParameterMode.IN)
        .registerStoredProcedureParameter("addList", String.class, ParameterMode.IN)
        .registerStoredProcedureParameter("removeList", String.class, ParameterMode.IN);

        query.setParameter("aid", dto.getAid())
        .setParameter("addList", addList)
        .setParameter("removeList", removeList);
    }
}
