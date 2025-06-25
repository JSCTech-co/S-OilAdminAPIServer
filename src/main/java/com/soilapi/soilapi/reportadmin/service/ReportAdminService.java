package com.soilapi.soilapi.reportadmin.service;

import org.springframework.stereotype.Service;

import com.soilapi.soilapi.reportadmin.dto.ReportAdminInsertRequest;
import com.soilapi.soilapi.reportadmin.dto.ReportAdminUpdateRequest;
import com.soilapi.soilapi.reportadmin.util.ReportProcedureBinder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.transaction.annotation.Transactional;

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
}
