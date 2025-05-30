package com.soilapi.soilapi.kpikopadmin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KpiKopAdminInsertRequest {
    private String compId;
    private String compType;          // CompType: KPI / KOP
    private String uom;
    private String uomKorean;
    private String etlJobName;
    private String nameKor;           // Name_KOR
    private String nameEng;           // Name_ENG → CompName 역할도 겸함
    private String widgetObjectId;
    private String overviewWidgetId;
}
