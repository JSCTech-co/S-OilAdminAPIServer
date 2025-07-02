package com.soilapi.soilapi.kpikopadmin.service;

import org.springframework.stereotype.Service;

import com.soilapi.soilapi.kpikopadmin.dto.KpiKopAdminInsertRequest;
import com.soilapi.soilapi.kpikopadmin.dto.KpiKopAdminSelectRequest;
import com.soilapi.soilapi.kpikopadmin.dto.KpiKopAdminSelectResponse;
import com.soilapi.soilapi.kpikopadmin.dto.KpiKopAdminUpdateRequest;
import com.soilapi.soilapi.kpikopadmin.util.ProcedureBinder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class KpiKopAdminService {

    @PersistenceContext
    private EntityManager eManager;

    @Transactional
    public void insertKpiKopAdmin(KpiKopAdminInsertRequest dto){
        try {
        StoredProcedureQuery query = eManager
            .createStoredProcedureQuery("sg_InsertKpiKopAdmin");
        
        ProcedureBinder.bindKpiKopAdminInsertParams(query, dto);
        query.execute();

        } catch (PersistenceException e) {
            // DB에서의 제약조건 오류, 트랜잭션 문제
            throw new RuntimeException("KPI/KOP 저장 중 DB 오류 발생", e);

        } catch (Exception e) {
            // 기타 모든 예외
            throw new RuntimeException("KPI/KOP 저장 중 예상치 못한 오류 발생", e);
        }
    }

    @Transactional
    public void updateKpiKopAdmin(KpiKopAdminUpdateRequest dto){
        try {
        StoredProcedureQuery query = eManager
            .createStoredProcedureQuery("sg_UpdateKpiKopAdmin");
        
        ProcedureBinder.bindKpiKopAdminUpdateParams(query, dto);
        query.execute();

        } catch (PersistenceException e) {
            // DB에서의 제약조건 오류, 트랜잭션 문제
            throw new RuntimeException("KPI/KOP 수정 중 DB 오류 발생", e);

        } catch (Exception e) {
            // 기타 모든 예외
            throw new RuntimeException("KPI/KOP 수정 중 예상치 못한 오류 발생", e);
        }
    }
    
    @Transactional
    public Page<KpiKopAdminSelectResponse> selectKpiKopAdmin(KpiKopAdminSelectRequest dto){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_SelectKpiKopAdmin");
            ProcedureBinder.binKpiKopAdminSelectParams(query, dto);
            query.execute();

            @SuppressWarnings("unchecked")
            List<Object[]> resultList = query.getResultList();
            List<KpiKopAdminSelectResponse> responseList = resultList.stream().map(row -> {
                KpiKopAdminSelectResponse response = new KpiKopAdminSelectResponse();
                response.setCid(((Number) row[0]).intValue());
                response.setCompId((String)row[1]);
                response.setNameENG((String)row[2]);
                response.setNameKOR((String)row[3]);
                response.setWidgetObjectId((String)row[4]);
                response.setOverviewWidgetId((String)row[5]);
                response.setUom((String)row[6]);
                response.setUomKorean((String)row[7]);
                response.setETLJobName((String)row[8]);
                response.setIsActive((char)row[9]);
                return response;
            }).collect(Collectors.toList());

            String baseQuery = "SELECT COUNT(*) FROM vw_KPIKOPAdmin WHERE isActive = :isActive";
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
            throw new RuntimeException("sg_SelectKpiKopAdmin 중 알 수 없는 오류 발생", e);
        }
    }
}