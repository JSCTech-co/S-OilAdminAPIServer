package com.soilapi.soilapi.appmapping.service;

import java.util.ArrayList;
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
import com.soilapi.soilapi.appmapping.dto.AppToCompMappingListResponse;
import com.soilapi.soilapi.appmapping.dto.AppToCompModifyRequest;
import com.soilapi.soilapi.appmapping.dto.AppToCompMappingListResponse.AppToCompInfo;
import com.soilapi.soilapi.appmapping.util.AppMappingProcedureBinder;
import com.soilapi.soilapi.comprolemapping.util.CompRoleDetailsProcedureBinder;

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

    public AppToCompMappingListResponse selectAppToCompMappingList(int aid){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_SelectAppToCompMappingList");
            
            AppMappingProcedureBinder.bindAppToCompMappingListParams(query, aid);
            query.execute();

            @SuppressWarnings("unchecked")
            List<Object[]> resultList = query.getResultList();

            List<AppToCompInfo> mappedList = new ArrayList<>();
            List<AppToCompInfo> unmappedList = new ArrayList<>();

            for(Object[] row : resultList){
                int cid = ((Number) row[0]).intValue();
                String compId = (String) row[1];
                String compType = (String) row[2];
                String nameKor = (String) row[3];
                String nameEng = (String) row[4];
                String mappingType = (String) row[5];

                AppToCompInfo comp = new AppToCompInfo();
                comp.setCid(cid);
                comp.setCompId(compId);
                comp.setCompType(compType);
                comp.setNameEng(nameEng);
                comp.setNameKor(nameKor);
                comp.setMappingType(mappingType);

                if("MAPPED".equalsIgnoreCase(mappingType)){
                    mappedList.add(comp);
                }else{
                    unmappedList.add(comp);
                }
            }

            AppToCompMappingListResponse response = new AppToCompMappingListResponse();
            response.setMappedList(mappedList);
            response.setUnmappedList(unmappedList);
            
            return response;
        }catch(Exception e){
            throw new RuntimeException("sg_ModifyAppToCompMapping 중 알 수 없는 오류 발생", e);
        }
    }

    public void modifyAppToCompList(AppToCompModifyRequest dto){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_ModifyAppToCompMapping");
            AppMappingProcedureBinder.bindAppToCompMappingModifyParams(query, dto);
            query.execute();
        }catch(Exception e){
            throw new RuntimeException("sg_ModifyAppToCompMapping 중 알 수 없는 오류 발생", e);
        }
    }
}
