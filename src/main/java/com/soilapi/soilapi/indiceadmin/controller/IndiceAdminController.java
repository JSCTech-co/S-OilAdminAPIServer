package com.soilapi.soilapi.indiceadmin.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soilapi.soilapi.indiceadmin.dto.IndiceAdminSelectRequest;
import com.soilapi.soilapi.indiceadmin.dto.IndiceAdminSelectResponse;
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
}
