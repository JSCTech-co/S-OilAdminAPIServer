package com.soilapi.soilapi.appmapping.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.soilapi.soilapi.appmapping.dto.AppMasterInsertRequest;
import com.soilapi.soilapi.appmapping.dto.AppMasterListRequest;
import com.soilapi.soilapi.appmapping.dto.AppMasterListResponse;
import com.soilapi.soilapi.appmapping.dto.AppMasterUpdateRequest;
import com.soilapi.soilapi.appmapping.util.AppMappingProcedureBinder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class AppMappingService {
    @PersistenceContext
    private EntityManager eManager;

    @Transactional(readOnly = true)
    public Page<AppMasterListResponse> selectAppMasterList(AppMasterListRequest dto){
        StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_SelectAppMaster");
        AppMappingProcedureBinder.bindAppMasterListParams(query, dto);
        query.execute();

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();
        List<AppMasterListResponse> responseList = resultList.stream().map(row -> {
            AppMasterListResponse response = new AppMasterListResponse();
            response.setAid(((Number) row[0]).intValue());
            response.setAppName((String) row[1]);
            response.setAppId((String) row[2]);
            response.setAppType((String) row[3]);
            response.setCompCount(((Number) row[4]).intValue());
            return response;
        }).collect(Collectors.toList());

        String baseQuery = "SELECT COUNT(*) FROM DSV_AppMaster WHERE 1=1";
        if("appName".equalsIgnoreCase(dto.getSearchType()) && StringUtils.hasText(dto.getSearchKeyword())){
            baseQuery += " AND AppName LIKE :searchKeyword";
        }else if("appType".equalsIgnoreCase(dto.getSearchType()) && StringUtils.hasText(dto.getSearchKeyword())){
            baseQuery += " AND AppType LIKE :searchKeyword";
        }

        Query countQuery = eManager.createNativeQuery(baseQuery);
        if(StringUtils.hasText(dto.getSearchType()) && StringUtils.hasText(dto.getSearchKeyword())){
            countQuery.setParameter("searchKeyword", "%" + dto.getSearchKeyword() + "%");
        }
        Long totalCount = ((Number) countQuery.getSingleResult()).longValue();
        Pageable pageable = PageRequest.of(dto.getPageNo() -1,  dto.getPageSize());
        return new PageImpl<>(responseList, pageable, totalCount);
    }

    public void insertAppMasterList(AppMasterInsertRequest dto){
        StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_InsertAppMaster");
        AppMappingProcedureBinder.bindAppMasterInsertParams(query, dto);
        query.execute();
    }

    public void updateAppMasterList(AppMasterUpdateRequest dto){
        StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_UpdateAppMaster");
        AppMappingProcedureBinder.bindAppMasterUpdateParams(query, dto);
        query.execute();
    }
}
