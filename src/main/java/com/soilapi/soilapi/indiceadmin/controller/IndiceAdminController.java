package com.soilapi.soilapi.indiceadmin.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soilapi.soilapi.indiceadmin.dto.IndiceAdminInsertRequest;
import com.soilapi.soilapi.indiceadmin.dto.IndiceAdminSelectRequest;
import com.soilapi.soilapi.indiceadmin.dto.IndiceAdminSelectResponse;
import com.soilapi.soilapi.indiceadmin.dto.IndiceAdminUpdateRequest;
import com.soilapi.soilapi.indiceadmin.service.IndiceAdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/indice")
public class IndiceAdminController {

    private final IndiceAdminService iaService;

    @GetMapping("/select")
    public Page<IndiceAdminSelectResponse> appList(@ModelAttribute IndiceAdminSelectRequest dto){
        return iaService.selectIndiceAdmin(dto);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody IndiceAdminInsertRequest dto){
        iaService.insertIndiceAdmin(dto);
        return ResponseEntity.ok("Indice Insert 완료.");
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody IndiceAdminUpdateRequest dto){
        iaService.updateIndiceAdmin(dto);
        return ResponseEntity.ok("Indice Update 완료.");
    }
}
