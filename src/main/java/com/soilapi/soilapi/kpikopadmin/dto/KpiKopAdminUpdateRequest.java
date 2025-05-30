package com.soilapi.soilapi.kpikopadmin.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KpiKopAdminUpdateRequest {
    private Integer cid;
    private String compId;
    private String compType;
    private String uom;
    private String uomKorean;
    private String etlJobName;
    private String nameKor;
    private String nameEng;
    private String widgetObjectId;
    private String overviewWidgetId;
    private String updatedBy;
    @Column(name = "isActive")
    private String isActive;
}
