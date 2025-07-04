package com.soilapi.soilapi.appmapping.controller;

import org.springframework.web.bind.annotation.RestController;

import com.soilapi.soilapi.appmapping.dto.AppMasterInsertRequest;
import com.soilapi.soilapi.appmapping.dto.AppMasterListRequest;
import com.soilapi.soilapi.appmapping.dto.AppMasterListResponse;
import com.soilapi.soilapi.appmapping.dto.AppMasterUpdateRequest;
import com.soilapi.soilapi.appmapping.service.AppMappingService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appmapping")
public class AppMappingController {
    private final AppMappingService amService;

    @GetMapping("/appList")
    public Page<AppMasterListResponse> appList(@ModelAttribute AppMasterListRequest dto){
        return amService.selectAppMasterList(dto);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody AppMasterInsertRequest dto){
        amService.insertAppMasterList(dto);
        return ResponseEntity.ok("AppMaster Insert 완료.");
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody AppMasterUpdateRequest dto){
        amService.updateAppMasterList(dto);
        return ResponseEntity.ok("AppMaster Update 완료.");
    }
}
