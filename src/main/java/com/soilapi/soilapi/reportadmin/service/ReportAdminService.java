package com.soilapi.soilapi.reportadmin.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.soilapi.soilapi.reportadmin.dto.ReportAdminInsertRequest;
import com.soilapi.soilapi.reportadmin.dto.ReportAdminSelectRequest;
import com.soilapi.soilapi.reportadmin.dto.ReportAdminSelectResponse;
import com.soilapi.soilapi.reportadmin.dto.ReportAdminUpdateRequest;
import com.soilapi.soilapi.reportadmin.util.ReportProcedureBinder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class ReportAdminService {
    @PersistenceContext
    private EntityManager eManager;

    @Transactional
    public void insertReportAdmin(ReportAdminInsertRequest dto){
        try {
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_InsertReportAdmin");
            
            ReportProcedureBinder.bindReportInsertParams(query, dto);
            query.execute();

        } catch (PersistenceException e) {
            // DB에서의 제약조건 오류, 트랜잭션 문제
            throw new RuntimeException("Report 저장 중 DB 오류 발생", e);

        } catch (Exception e) {
            // 기타 모든 예외
            throw new RuntimeException("Report 저장 중 예상치 못한 오류 발생", e);
        }
    }

    @Transactional
    public void updateReportAdmin(ReportAdminUpdateRequest dto){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_UpdateReportAdmin");

            ReportProcedureBinder.bindReportUpdateParam(query, dto);
            query.execute();
            
        }catch(PersistenceException e){
            throw new RuntimeException("Report 수정 중 DB 오류 발생", e);
        }catch(Exception e){
            throw new RuntimeException("Report 수정 중 예상치 못한 오류 발생", e);
        }
    }

    @Transactional
    public Page<ReportAdminSelectResponse> selectReportAdmin(ReportAdminSelectRequest dto){
        try{
            StoredProcedureQuery query = eManager.createStoredProcedureQuery("sg_SelectReportAdmin");
            ReportProcedureBinder.bindReportSelectParams(query, dto);
            query.execute();
            @SuppressWarnings("unchecked")
            List<Object[]> resultList = query.getResultList();
            List<ReportAdminSelectResponse> responseList = resultList.stream().map(row -> {
                ReportAdminSelectResponse response = new ReportAdminSelectResponse();
                response.setCompId((String)row[0]);
                response.setCompName((String)row[1]);
                response.setCompNameKorean((String)row[2]);
                response.setSourceSys((String)row[3]);
                response.setSourceSysName((String)row[4]);
                response.setOwnerDept((String)row[5]);
                response.setHyperLink((String)row[6]);
                response.setIsActive((char)row[7]);
                response.setEtlJobName((String)row[8]);
                response.setCompSeq(((Number)row[9]).intValue());
                return response;
            }).collect(Collectors.toList());

            String baseQuery = "SELECT COUNT(*) FROM vw_ReportAdmin WHERE isActive= :isActive";
            
            if ("compId".equalsIgnoreCase(dto.getSearchType()) && StringUtils.hasText(dto.getSearchType()) ){
                baseQuery += " AND compId LIKE :searchKeyword";
            }else if ("compName".equalsIgnoreCase(dto.getSearchType()) && StringUtils.hasText(dto.getSearchType()) ){
                baseQuery += " AND compName LIKE :searchKeyword";
            }else if ("compNameKorean".equalsIgnoreCase(dto.getSearchType()) && StringUtils.hasText(dto.getSearchType()) ){
                baseQuery += " AND compNameKorean LIKE :searchKeyword";
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
            throw new RuntimeException("sg_SelectReportAdmin 중 알 수 없는 오류 발생", e);
        }
    }
}

