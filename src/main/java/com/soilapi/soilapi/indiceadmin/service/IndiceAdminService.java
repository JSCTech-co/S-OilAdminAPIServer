package com.soilapi.soilapi.indiceadmin.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.soilapi.soilapi.indiceadmin.dto.IndiceAdminSelectRequest;
import com.soilapi.soilapi.indiceadmin.dto.IndiceAdminSelectResponse;
import com.soilapi.soilapi.indiceadmin.util.IndiceAdminProcedureBinder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;

@Service
public class IndiceAdminService {
    @PersistenceContext
    private EntityManager eManager;

    @Transactional
    public Page<IndiceAdminSelectResponse> selectIndiceAdmin(IndiceAdminSelectRequest dto){
         try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_SelectIndiceAdmin");
            IndiceAdminProcedureBinder.bindIndiceAdminSelectParams(query, dto);
            query.execute();

            @SuppressWarnings("unchecked")
            List<Object[]> resultList = query.getResultList();
            List<IndiceAdminSelectResponse> responseList = resultList.stream().map(row -> {
                IndiceAdminSelectResponse response = new IndiceAdminSelectResponse();
                response.setCid(((Number) row[0]).intValue());
                response.setCompId((String)row[1]);
                response.setCompType((String)row[2]);
                response.setNameENG((String)row[3]);
                response.setNameKOR((String)row[4]);
                response.setCurrentMonth((String)row[5]);
                response.setFlag((String)row[6]);
                response.setSecondaryName((String)row[7]);
                response.setSecondaryValue((String)row[8]);
                response.setUom((String)row[9]);
                response.setUomKorean((String)row[10]);
                response.setETLJobName((String)row[11]);
                return response;
            }).collect(Collectors.toList());

            String baseQuery = "SELECT COUNT(*) FROM vw_KPIKOPIndiceAdmin WHERE 1=1 ";
            if ("compId".equalsIgnoreCase(dto.getSearchType()) && StringUtils.hasText(dto.getSearchType()) ){
                baseQuery += "AND compId LIKE :searchKeyword";
            }else if ("nameENG".equalsIgnoreCase(dto.getSearchType()) && StringUtils.hasText(dto.getSearchType()) ){
                baseQuery += "AND NAME_ENG LIKE :searchKeyword";
            }else if ("nameKOR".equalsIgnoreCase(dto.getSearchType()) && StringUtils.hasText(dto.getSearchType()) ){
                baseQuery += "AND NAME_KOR LIKE :searchKeyword";
            }
            
            Query countQuery = eManager.createNativeQuery(baseQuery);
            if(StringUtils.hasText(dto.getSearchKeyword())){
                countQuery.setParameter("searchKeyword", "%"+dto.getSearchKeyword()+"%");
            }
            Long totalCount = ((Number) countQuery.getSingleResult()).longValue();
            Pageable pageable = PageRequest.of(dto.getPageNo() - 1, dto.getPageSize());
            return new PageImpl<>(responseList, pageable, totalCount);
        }catch(Exception e){
            throw new RuntimeException("sg_SelectIndiceAdmin 중 알 수 없는 오류 발생", e);
        }
    }

}
