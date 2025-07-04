package com.soilapi.soilapi.kpikopfilteradmin.controller;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soilapi.soilapi.kpikopfilteradmin.dto.KpiKopFilterAdminInsertRequest;
import com.soilapi.soilapi.kpikopfilteradmin.dto.KpiKopFilterAdminSelectRequest;
import com.soilapi.soilapi.kpikopfilteradmin.dto.KpiKopFilterAdminSelectResponse;
import com.soilapi.soilapi.kpikopfilteradmin.dto.KpiKopFilterAdminUpdateRequest;
import com.soilapi.soilapi.kpikopfilteradmin.service.KpiKopFilterAdminService;

import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody KpiKopFilterAdminInsertRequest dto){
        kfaService.insertKpiKopFilterAdmin(dto);
        return ResponseEntity.ok("Filter 등록 완료");
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody KpiKopFilterAdminUpdateRequest dto){
        kfaService.updateKpiKopFilterAdmin(dto);
        return ResponseEntity.ok("Filter 수정 완료");
    }

    @GetMapping("/delete")
    public ResponseEntity<String> delete(int filterId){
        kfaService.deleteKpiKopFilterAdmin(filterId);
        return ResponseEntity.ok("Filter 삭제 완료");
    }
}
