package com.soilapi.soilapi.kpikopadmin.service;

import org.springframework.stereotype.Service;

import com.soilapi.soilapi.kpikopadmin.dto.KpiKopAdminInsertRequest;
import com.soilapi.soilapi.kpikopadmin.dto.KpiKopAdminUpdateRequest;
import com.soilapi.soilapi.kpikopadmin.util.ProcedureBinder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;

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
            throw new RuntimeException("KPI/KOP 수정정 중 예상치 못한 오류 발생", e);
        }
    }
    
}