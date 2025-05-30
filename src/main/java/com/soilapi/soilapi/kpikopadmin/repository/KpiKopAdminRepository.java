package com.soilapi.soilapi.kpikopadmin.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soilapi.soilapi.kpikopadmin.entity.KpiKopAdminEntity;

@Repository
public interface KpiKopAdminRepository extends JpaRepository<KpiKopAdminEntity, Long> {
    // 전체 리스트 (페이징)
    Page<KpiKopAdminEntity> findAll(Pageable pageable);
    
    // CompID Like 검색 + CID 정렬
    Page<KpiKopAdminEntity> findByCompIdContainingIgnoreCase(String keyword, Pageable pageable);

    // Name_KOR LIKE 검색 + CID 정렬
    Page<KpiKopAdminEntity> findByNameKorContainingIgnoreCase(String keyword, Pageable pageable);

    // Name_ENG LIKE 검색 + CID 정렬
    Page<KpiKopAdminEntity> findByNameEngContainingIgnoreCase(String keyword, Pageable pageable);

    // 전체 리스트 중 isActive 값으로 필터링
    Page<KpiKopAdminEntity> findByIsActive(String isActive, Pageable pageable);

    // CompID + isActive
    Page<KpiKopAdminEntity> findByCompIdContainingIgnoreCaseAndIsActive(String keyword, String isActive, Pageable pageable);

    // Name_KOR + isActive
    Page<KpiKopAdminEntity> findByNameKorContainingIgnoreCaseAndIsActive(String keyword, String isActive, Pageable pageable);

    // Name_ENG + isActive
    Page<KpiKopAdminEntity> findByNameEngContainingIgnoreCaseAndIsActive(String keyword, String isActive, Pageable pageable);


}
