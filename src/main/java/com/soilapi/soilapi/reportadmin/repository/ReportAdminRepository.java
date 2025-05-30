package com.soilapi.soilapi.reportadmin.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soilapi.soilapi.reportadmin.entity.ReportAdminEntity;

@Repository
public interface ReportAdminRepository extends JpaRepository<ReportAdminEntity, String> {
    Page<ReportAdminEntity> findByIsActive(String isActive, Pageable pageable);

    // CompID + isActive
    Page<ReportAdminEntity> findByCompIdContainingIgnoreCaseAndIsActive(String keyword, String isActive, Pageable pageable);

    // Name_KOR + isActive
    Page<ReportAdminEntity> findByCompNameContainingIgnoreCaseAndIsActive(String keyword, String isActive, Pageable pageable);

    // Name_ENG + isActive
    Page<ReportAdminEntity> findByCompNameKoreanContainingIgnoreCaseAndIsActive(String keyword, String isActive, Pageable pageable);

}
