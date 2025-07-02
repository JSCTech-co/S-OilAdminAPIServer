package com.soilapi.soilapi.reportadmin.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.soilapi.soilapi.reportadmin.dto.ReportAdminInsertRequest;
import com.soilapi.soilapi.reportadmin.dto.ReportAdminSelectRequest;
import com.soilapi.soilapi.reportadmin.dto.ReportAdminSelectResponse;
import com.soilapi.soilapi.reportadmin.dto.ReportAdminUpdateRequest;
import com.soilapi.soilapi.reportadmin.entity.ReportAdminEntity;
import com.soilapi.soilapi.reportadmin.repository.ReportAdminRepository;
import com.soilapi.soilapi.reportadmin.service.ReportAdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ReportAdmin")
public class ReportAdminController {
    private final ReportAdminRepository raRepo;
    private final ReportAdminService raService;

    @GetMapping("/getAllList")
    public Page<ReportAdminEntity> getAllList(@RequestParam String isActive,
                                              @RequestParam int page,
                                              @RequestParam int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("compSeq").ascending());
        
        return raRepo.findByIsActive(isActive, pageable);
    }

    @GetMapping("/searchList")
    public Page<ReportAdminEntity> searchList(@RequestParam String column, 
                                              @RequestParam String keyword,
                                              @RequestParam String isActive,
                                              @RequestParam int page,
                                              @RequestParam int size)
    {
        Pageable pageable = PageRequest.of(page, size, Sort.by("compSeq").ascending());
        if(column.equalsIgnoreCase("compid")){
            return raRepo.findByCompIdContainingIgnoreCaseAndIsActive(keyword, isActive, pageable);
        }else if(column.equalsIgnoreCase("compname")){
            return raRepo.findByCompNameContainingIgnoreCaseAndIsActive(keyword, isActive, pageable);
        }if(column.equalsIgnoreCase("compnamekorean")){
            return raRepo.findByCompNameKoreanContainingIgnoreCaseAndIsActive(keyword, isActive, pageable);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "검색 컬럼명이 유효하지 않습니다: " + column);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody ReportAdminInsertRequest request){
        raService.insertReportAdmin(request);
        return ResponseEntity.ok("Report 등록 완료");
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody ReportAdminUpdateRequest request) {
        raService.updateReportAdmin(request);
        return ResponseEntity.ok("Report 수정 완료");
    }

    @GetMapping("/select")
    public Page<ReportAdminSelectResponse> select(@ModelAttribute ReportAdminSelectRequest request){
        return raService.selectReportAdmin(request);
    }
    
}
