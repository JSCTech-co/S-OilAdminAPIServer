package com.soilapi.soilapi.indiceadmin.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.soilapi.soilapi.indiceadmin.dto.IndiceAdminInsertRequest;
import com.soilapi.soilapi.indiceadmin.dto.IndiceAdminSelectRequest;
import com.soilapi.soilapi.indiceadmin.dto.IndiceAdminSelectResponse;
import com.soilapi.soilapi.indiceadmin.dto.IndiceAdminUpdateRequest;
import com.soilapi.soilapi.indiceadmin.util.IndiceAdminProcedureBinder;
import com.soilapi.soilapi.kpikopadmin.util.ProcedureBinder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
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
                response.setNameEng((String)row[3]);
                response.setNameKor((String)row[4]);
                response.setCurrentMonth((String)row[5]);
                response.setFlag((String)row[6]);
                response.setSecondaryName((String)row[7]);
                response.setSecondaryValue((String)row[8]);
                response.setUom((String)row[9]);
                response.setUomKorean((String)row[10]);
                response.setEtlJobName((String)row[11]);
                response.setIsActive((char)row[12]);
                return response;
            }).collect(Collectors.toList());

            String baseQuery = "SELECT COUNT(*) FROM vw_KPIKOPIndiceAdmin WHERE isActive = :isActive ";
            if ("compId".equalsIgnoreCase(dto.getSearchType()) && StringUtils.hasText(dto.getSearchType()) ){
                baseQuery += "AND compId LIKE :searchKeyword";
            }else if ("nameENG".equalsIgnoreCase(dto.getSearchType()) && StringUtils.hasText(dto.getSearchType()) ){
                baseQuery += "AND NAME_ENG LIKE :searchKeyword";
            }else if ("nameKOR".equalsIgnoreCase(dto.getSearchType()) && StringUtils.hasText(dto.getSearchType()) ){
                baseQuery += "AND NAME_KOR LIKE :searchKeyword";
            }
            
            Query countQuery = eManager.createNativeQuery(baseQuery);
            if(StringUtils.hasText(dto.getIsActive())){
                countQuery.setParameter("isActive", dto.getIsActive());
            }
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

    public void insertIndiceAdmin(IndiceAdminInsertRequest dto){
        try {
        StoredProcedureQuery query = eManager
            .createStoredProcedureQuery("sg_InsertIndiceAdmin");
        IndiceAdminProcedureBinder.bindIndiceAdminInsertParams(query, dto);

        query.execute();

        } catch (PersistenceException e) {
            // DB에서의 제약조건 오류, 트랜잭션 문제
            throw new RuntimeException("Indice 삽입 중 DB 오류 발생", e);

        } catch (Exception e) {
            // 기타 모든 예외
            throw new RuntimeException("Indice 삽입 중 예상치 못한 오류 발생", e);
        }
    }

    public void updateIndiceAdmin(IndiceAdminUpdateRequest dto){
        try {
        StoredProcedureQuery query = eManager
            .createStoredProcedureQuery("sg_UpdateIndiceAdmin");
        System.out.println(dto);
        IndiceAdminProcedureBinder.bindIndiceAdminUpdateParams(query, dto);

        query.execute();

        } catch (PersistenceException e) {
            // DB에서의 제약조건 오류, 트랜잭션 문제
            throw new RuntimeException("Indice 수정 중 DB 오류 발생", e);

        } catch (Exception e) {
            // 기타 모든 예외
            throw new RuntimeException("Indice 수정 중 예상치 못한 오류 발생", e);
        }
    }

}
