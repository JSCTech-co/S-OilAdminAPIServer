package com.soilapi.soilapi.roleadmin.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import com.soilapi.soilapi.roleadmin.dto.RoleAdminListRequest;
import com.soilapi.soilapi.roleadmin.dto.RoleAdminListResponse;
import com.soilapi.soilapi.roleadmin.util.RoleAdminProcedureBinder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleAdminService {
    @PersistenceContext
    private EntityManager eManager;

    public Page<RoleAdminListResponse> selectRoleAdmin(RoleAdminListRequest dto){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_SelectRoleAdmin");
            RoleAdminProcedureBinder.bindRoleAdminParams(query, dto);
            query.execute();

            @SuppressWarnings("unchecked")
            List<Object[]> resultList = query.getResultList();
            List<RoleAdminListResponse> responseList = resultList.stream().map(row -> {
                RoleAdminListResponse response = new RoleAdminListResponse();
                response.setRoleId(row[0] == null ? null : ((Number) row[0]).intValue());
                response.setRoleName((String) row[1]);
                response.setSharepointId(row[2] == null ? null : ((Number) row[2]).intValue());
                return response;
            }).collect(Collectors.toList());

            String baseQuery = "SELECT COUNT(DISTINCT RoleID) AS TotalCount FROM DSVRoleMaster WHERE 1=1 ";
            String searchType = dto.getSearchType();
            if (StringUtils.hasText(searchType)) {
                baseQuery += "AND " + searchType + " LIKE :searchKeyword";
            }
            Query countQuery = eManager.createNativeQuery(baseQuery);
            if (StringUtils.hasText(dto.getSearchKeyword())) {
                countQuery.setParameter("searchKeyword", "%" + dto.getSearchKeyword() + "%");
            }
            Long totalCount = ((Number) countQuery.getSingleResult()).longValue();
            Pageable pageable = PageRequest.of(dto.getPageNo() - 1, dto.getPageSize());
            return new PageImpl<>(responseList, pageable, totalCount);
        }catch(Exception e){
            throw new RuntimeException("selectRoleAdmin 중 문제 발생.", e);
        }
    }
}
