package com.soilapi.soilapi.overview.controller;

import com.soilapi.soilapi.overview.dto.OverviewListRequest;
import com.soilapi.soilapi.overview.dto.OverviewListResponse;
import com.soilapi.soilapi.overview.dto.OverviewSelectIndiceListRequest;
import com.soilapi.soilapi.overview.dto.OverviewSelectIndiceListResponse;
import com.soilapi.soilapi.overview.dto.OverviewSelectWidgetListRequest;
import com.soilapi.soilapi.overview.dto.OverviewSelectWidgetListResponse;
import com.soilapi.soilapi.overview.dto.OverviewUpdateIndiceRequest;
import com.soilapi.soilapi.overview.dto.OverviewUpdateWidgetRequest;
import com.soilapi.soilapi.overview.service.OverviewService;


import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/overview")
@RequiredArgsConstructor
public class OverviewController {

    private final OverviewService overviewService;

    @GetMapping("/select")
    public Page<OverviewListResponse> select(@ModelAttribute OverviewListRequest dto) {
        return overviewService.selectOverviewAdmin(dto);
    }

    @GetMapping("/selectIndiceList")
    public List<OverviewSelectIndiceListResponse> selectIndiceList(@ModelAttribute OverviewSelectIndiceListRequest dto){
        return overviewService.selectIndiceList(dto);
    }

    @GetMapping("/selectWidgetList")
    public List<OverviewSelectWidgetListResponse> selectWidgetList(@ModelAttribute OverviewSelectWidgetListRequest dto){
        return overviewService.selectWidgetList(dto);
    }

    @PostMapping("/updateWidget")
    public ResponseEntity<String> updateWidget(@RequestBody OverviewUpdateWidgetRequest dto){
        overviewService.updateWidget(dto);
        return ResponseEntity.ok("OveriveWidget Update 완료.");
    }

    @PostMapping("/updateIndice")
    public ResponseEntity<String> updateIndice(@RequestBody OverviewUpdateIndiceRequest dto){
        overviewService.updateIndice(dto);
        return ResponseEntity.ok("OveriveIndice Update 완료.");
    }
}
