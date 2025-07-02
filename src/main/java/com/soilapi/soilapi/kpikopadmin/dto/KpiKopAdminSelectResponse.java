package com.soilapi.soilapi.kpikopadmin.dto;

import lombok.Data;

@Data
public class KpiKopAdminSelectResponse {
    private int cid;
    private String compId;
    private String nameENG;
    private String nameKOR;
    private String widgetObjectId;
    private String overviewWidgetId;
    private String uom;
    private String uomKorean;
    private String ETLJobName;
    private char isActive;
}
