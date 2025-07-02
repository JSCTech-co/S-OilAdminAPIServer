package com.soilapi.soilapi.kpikopfilteradmin.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soilapi.soilapi.kpikopfilteradmin.dto.KpiKopFilterAdminSelectRequest;
import com.soilapi.soilapi.kpikopfilteradmin.dto.KpiKopFilterAdminSelectResponse;
import com.soilapi.soilapi.kpikopfilteradmin.service.KpiKopFilterAdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kpikopfilter")
public class KpiKopFilterAdminController {
    private final KpiKopFilterAdminService kfaService;

    @GetMapping("/select")
    public Page<KpiKopFilterAdminSelectResponse> select(@ModelAttribute KpiKopFilterAdminSelectRequest dto){
        return kfaService.selectKpiKopFilterAdmin(dto);
    }
}
