package com.soilapi.soilapi.comprolemapping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soilapi.soilapi.comprolemapping.entity.CompRoleDetailsView;

public interface CompRoleDetailsViewRepository extends JpaRepository<CompRoleDetailsView, String>{
    List<CompRoleDetailsView> findAll();

    // 1. RoleName LIKE 검색 + CompID 정렬
    List<CompRoleDetailsView> findByRoleNameContainingIgnoreCaseOrderByCompIdAsc(String roleName);

    // 2. CompName LIKE 검색 + RoleID 정렬
    List<CompRoleDetailsView> findByCompNameContainingIgnoreCaseOrderByRoleIdAsc(String compName);

}
