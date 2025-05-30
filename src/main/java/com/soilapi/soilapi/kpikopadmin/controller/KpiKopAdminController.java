package com.soilapi.soilapi.kpikopadmin.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.soilapi.soilapi.kpikopadmin.dto.KpiKopAdminInsertRequest;
import com.soilapi.soilapi.kpikopadmin.dto.KpiKopAdminUpdateRequest;
import com.soilapi.soilapi.kpikopadmin.entity.KpiKopAdminEntity;
import com.soilapi.soilapi.kpikopadmin.repository.KpiKopAdminRepository;
import com.soilapi.soilapi.kpikopadmin.service.KpiKopAdminService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping("/KpiKopAdmin")
public class KpiKopAdminController {
    private final KpiKopAdminRepository kaRepo;
    private final KpiKopAdminService kaService;

    @GetMapping("/getAllList")
    public Page<KpiKopAdminEntity> getMethodName(@RequestParam String isActive, 
                                                 @RequestParam int page,
                                                 @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("cid").descending());
        return kaRepo.findByIsActive(isActive, pageable);
    }
    
    @GetMapping("/searchList")
    public Page<KpiKopAdminEntity> searchList(  @RequestParam String column, 
                                                @RequestParam String keyword,
                                                @RequestParam String isActive,
                                                @RequestParam int page,
                                                @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("cid").descending());
        if(column.equalsIgnoreCase("compid")){
            return kaRepo.findByCompIdContainingIgnoreCaseAndIsActive(keyword, isActive, pageable);
        }else if(column.equalsIgnoreCase("namekor")){
            return kaRepo.findByCompIdContainingIgnoreCaseAndIsActive(keyword, isActive, pageable);
        }else if(column.equalsIgnoreCase("nameeng")){
            return kaRepo.findByCompIdContainingIgnoreCaseAndIsActive(keyword, isActive, pageable);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "검색 컬럼명이 유효하지 않습니다: " + column);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody KpiKopAdminInsertRequest request) {
        kaService.insertKpiKopAdmin(request);
        return ResponseEntity.ok("KPI/KOP 등록 완료");
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody KpiKopAdminUpdateRequest request) {
        kaService.updateKpiKopAdmin(request);
        return ResponseEntity.ok("KPI/KOP 수정 완료");
    }
    
}
