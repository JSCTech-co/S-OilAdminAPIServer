package com.soilapi.soilapi.comprolemapping.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soilapi.soilapi.comprolemapping.dto.CompToRoleListRequest;
import com.soilapi.soilapi.comprolemapping.dto.CompToRoleListResponse;
import com.soilapi.soilapi.comprolemapping.dto.CompToRoleMappingListResponse;
import com.soilapi.soilapi.comprolemapping.dto.CompToRoleModifyRequest;
import com.soilapi.soilapi.comprolemapping.dto.RoleToCompListRequest;
import com.soilapi.soilapi.comprolemapping.dto.RoleToCompListResponse;
import com.soilapi.soilapi.comprolemapping.dto.RoleToCompMappingListResponse;
import com.soilapi.soilapi.comprolemapping.dto.RoleToCompModifyRequest;
import com.soilapi.soilapi.comprolemapping.util.CompRoleDetailsProcedureBinder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;



@Service
public class CompRoleDetailsService {

    @PersistenceContext
    private EntityManager eManager;

    @Transactional(readOnly = true)
    public Page<RoleToCompListResponse> selectRoleList(RoleToCompListRequest dto){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_SelectRoleToCompRoleList");
            CompRoleDetailsProcedureBinder.bindRoleToCompListParams(query, dto);
            query.execute();

            
            @SuppressWarnings("unchecked")
            List<Object[]> resultList = query.getResultList();

            List<RoleToCompListResponse> responseList = resultList.stream().map(row -> {
                RoleToCompListResponse response = new RoleToCompListResponse();
                response.setRoleId(((Number) row[0]).intValue());
                response.setRoleName((String) row[1]);
                response.setComponentCount(((Number) row[2]).intValue());
                return response;
            }).collect(Collectors.toList());

            // 검색조건과 일치하도록 count 쿼리 동기화
            String baseQuery = "SELECT COUNT(*) FROM DSVRoleMaster WHERE 1 = 1 ";
            if ("roleName".equalsIgnoreCase(dto.getSearchType()) && dto.getSearchKeyword() != null) {
                baseQuery += "AND RoleName LIKE :searchKeyword ";
            } else if ("roleId".equalsIgnoreCase(dto.getSearchType()) && dto.getSearchKeyword() != null) {
                baseQuery += "AND RoleID = :roleId ";
            }

            Query countQuery = eManager.createNativeQuery(baseQuery);
            if ("roleName".equalsIgnoreCase(dto.getSearchType()) && dto.getSearchKeyword() != null) {
                countQuery.setParameter("searchKeyword", "%" + dto.getSearchKeyword() + "%");
            } else if ("roleId".equalsIgnoreCase(dto.getSearchType()) && dto.getSearchKeyword() != null) {
                countQuery.setParameter("roleId", Integer.parseInt(dto.getSearchKeyword()));
            }

            Long totalCount = ((Number) countQuery.getSingleResult()).longValue();
            Pageable pageable = PageRequest.of(dto.getPageNo() - 1, dto.getPageSize());
            return new PageImpl<>(responseList, pageable, totalCount);

        }catch(Exception e){
            throw new RuntimeException("RoleToComp Select 중 알 수 없는 오류 발생", e);
        }
    }

    public RoleToCompMappingListResponse selectRoleToCompMappingList(int roleId){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_SelectRoleToCompMappingList");
            CompRoleDetailsProcedureBinder.bindRoleToCompMappingListParams(query, roleId);
            query.execute();

            @SuppressWarnings("unchecked")
            List<Object[]> resultList = query.getResultList();

            List<RoleToCompMappingListResponse.CompInfo> mappedList = new ArrayList<>();
            List<RoleToCompMappingListResponse.CompInfo> unmappedList = new ArrayList<>();

            for (Object[] row : resultList) {
                String compId = (String) row[0];
                String compType = (String) row[1];
                String compName = (String) row[2];
                String mappingType = (String) row[3]; // 'MAPPED' or 'UNMAPPED'
            
                RoleToCompMappingListResponse.CompInfo comp = new RoleToCompMappingListResponse.CompInfo();
                comp.setCompId(compId);
                comp.setCompType(compType);
                comp.setCompName(compName);
            
                if ("MAPPED".equalsIgnoreCase(mappingType)) {
                    mappedList.add(comp);
                } else {
                    unmappedList.add(comp);
                }
            }

            RoleToCompMappingListResponse response = new RoleToCompMappingListResponse();
            response.setMappedList(mappedList);
            response.setUnmappedList(unmappedList);

            return response;
        }catch(Exception e){
            throw new RuntimeException("selectRoleToCompMappingList 중 알 수 없는 오류 발생", e);
        }
    }

    public void modifyRoleToComp(RoleToCompModifyRequest dto){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_ModifyRoleToCompMapping");
            CompRoleDetailsProcedureBinder.bindRoleToCompModifyParams(query, dto);
            query.execute();
        }catch(Exception e){
            throw new RuntimeException("sg_ModifyRoleToCompMapping 중 알 수 없는 오류 발생", e);
        }
    }


    @Transactional(readOnly = true)
    public Page<CompToRoleListResponse> selectCompList(CompToRoleListRequest dto){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_SelectCompToRoleCompList");
            CompRoleDetailsProcedureBinder.bindCompToRoleListParams(query, dto);
            query.execute();

            @SuppressWarnings("unchecked")
            List<Object[]> resultList = query.getResultList();

            List<CompToRoleListResponse> responseList = resultList.stream().map(row -> {
                CompToRoleListResponse response = new CompToRoleListResponse();
                response.setCompId((String) row[0]);
                response.setCompType((String) row[1]);
                response.setCompName((String) row[2]);
                response.setRoleCount(((Number) row[3]).intValue());
                return response;
            }).collect(Collectors.toList());

            // 검색조건과 일치하도록 count 쿼리 동기화
            String baseQuery = "SELECT COUNT(*) FROM DSVCompMaster WHERE isActive='Y' ";
            if ("compId".equalsIgnoreCase(dto.getSearchType()) && dto.getSearchKeyword() != null) {
                baseQuery += "AND CompId LIKE :searchKeyword ";
            } else if ("compType".equalsIgnoreCase(dto.getSearchType()) && dto.getSearchKeyword() != null) {
                baseQuery += "AND CompType LIKE :searchKeyword ";
            } else if ("compName".equalsIgnoreCase(dto.getSearchType()) && dto.getSearchKeyword() != null) {
                baseQuery += "AND compName LIKE :searchKeyword ";
            }
            Query countQuery = eManager.createNativeQuery(baseQuery);
            if (dto.getSearchKeyword() != null && !dto.getSearchKeyword().isBlank()) {
                countQuery.setParameter("searchKeyword", "%" + dto.getSearchKeyword() + "%");
            } 

            Long totalCount = ((Number) countQuery.getSingleResult()).longValue();
            Pageable pageable = PageRequest.of(dto.getPageNo() - 1, dto.getPageSize());
            return new PageImpl<>(responseList, pageable, totalCount);
        }catch(Exception e){
            throw new RuntimeException("sg_SelectCompToRoleCompList 중 알 수 없는 오류 발생", e);
        }
    }

    public CompToRoleMappingListResponse selectCompToRoleMappingList(String compId){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_SelectCompToRoleMappingList");
            CompRoleDetailsProcedureBinder.bindCompToRoleMappingListParams(query, compId);
            query.execute();

            @SuppressWarnings("unchecked")
            List<Object[]> resultList = query.getResultList();

            List<CompToRoleMappingListResponse.RoleInfo> mappedList = new ArrayList<>();
            List<CompToRoleMappingListResponse.RoleInfo> unmappedList = new ArrayList<>();

            for (Object[] row : resultList) {
                int roleId = ((Number) row[0]).intValue();
                String roleName = (String) row[1];
                String mappingType = (String) row[2];

                CompToRoleMappingListResponse.RoleInfo roleInfo = new CompToRoleMappingListResponse.RoleInfo();
                roleInfo.setRoleId(roleId);
                roleInfo.setRoleName(roleName); 

                if ("MAPPED".equalsIgnoreCase(mappingType)) {
                    mappedList.add(roleInfo);
                } else {
                    unmappedList.add(roleInfo);
                }
            }

            CompToRoleMappingListResponse response = new CompToRoleMappingListResponse();
            response.setMappedList(mappedList);
            response.setUnmappedList(unmappedList);

            return response;
        }catch(Exception e){
            throw new RuntimeException("sg_SelectCompToRoleMappingList 중 알 수 없는 오류 발생", e);
        }
    }

    public void modifyCompToRole(CompToRoleModifyRequest dto){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_ModifyCompToRoleMapping");
            CompRoleDetailsProcedureBinder.bindCompToRoleModifyParams(query, dto);
            query.execute();
        }catch(Exception e){
            throw new RuntimeException("sg_ModifyCompToRoleMapping 중 알 수 없는 오류 발생", e);
        }
    }

}
