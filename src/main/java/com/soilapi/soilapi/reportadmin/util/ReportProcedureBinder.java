package com.soilapi.soilapi.reportadmin.util;

import com.soilapi.soilapi.reportadmin.dto.ReportAdminInsertRequest;
import com.soilapi.soilapi.reportadmin.dto.ReportAdminUpdateRequest;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

public class ReportProcedureBinder {
    public static void bindReportInsertParams(StoredProcedureQuery query, ReportAdminInsertRequest dto) {
        query
        .registerStoredProcedureParameter("CompID", String.class, jakarta.persistence.ParameterMode.IN)
        .registerStoredProcedureParameter("CompName", String.class, jakarta.persistence.ParameterMode.IN)
        .registerStoredProcedureParameter("CompNameKorean", String.class, jakarta.persistence.ParameterMode.IN)
        .registerStoredProcedureParameter("SourceSys", String.class, jakarta.persistence.ParameterMode.IN)
        .registerStoredProcedureParameter("SourceSysName", String.class, jakarta.persistence.ParameterMode.IN)
        .registerStoredProcedureParameter("OwnerDept", String.class, jakarta.persistence.ParameterMode.IN)
        .registerStoredProcedureParameter("HyperLink", String.class, jakarta.persistence.ParameterMode.IN)
        .registerStoredProcedureParameter("ETLJobName", String.class, jakarta.persistence.ParameterMode.IN)
        .registerStoredProcedureParameter("IsActive", String.class, jakarta.persistence.ParameterMode.IN)
        .registerStoredProcedureParameter("CreatedBy", String.class, jakarta.persistence.ParameterMode.IN)

        .setParameter("CompID", dto.getCompId())
        .setParameter("CompName", dto.getCompName())
        .setParameter("CompNameKorean", dto.getCompNameKorean())
        .setParameter("SourceSys", dto.getSourceSys())
        .setParameter("SourceSysName", dto.getSourceSysName())
        .setParameter("OwnerDept", dto.getOwnerDept())
        .setParameter("HyperLink", dto.getHyperLink())
        .setParameter("ETLJobName", dto.getEtlJobName())
        .setParameter("IsActive", dto.getIsActive())
        .setParameter("CreatedBy", dto.getCreatedBy());
    }

    public static void bindReportUpdateParam(StoredProcedureQuery query, ReportAdminUpdateRequest dto){
        query.registerStoredProcedureParameter("CompID", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("CompName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("CompNameKorean", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("SourceSys", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("SourceSysName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("OwnerDept", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("HyperLink", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("ETLJobName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("UpdatedBy", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IsActive", String.class, ParameterMode.IN);

        query.setParameter("CompID", dto.getCompId());
        query.setParameter("CompName", dto.getCompName());
        query.setParameter("CompNameKorean", dto.getCompNameKorean());
        query.setParameter("SourceSys", dto.getSourceSys());
        query.setParameter("SourceSysName", dto.getSourceSysName());
        query.setParameter("OwnerDept", dto.getOwnerDept());
        query.setParameter("HyperLink", dto.getHyperLink());
        query.setParameter("ETLJobName", dto.getEtlJobName());
        query.setParameter("UpdatedBy", dto.getUpdatedBy());
        query.setParameter("IsActive", dto.getIsActive());
    }
}
