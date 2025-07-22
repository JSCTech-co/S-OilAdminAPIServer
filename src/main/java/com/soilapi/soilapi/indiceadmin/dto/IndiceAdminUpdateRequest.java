package com.soilapi.soilapi.indiceadmin.dto;

import lombok.Data;

@Data
public class IndiceAdminUpdateRequest {
    private String compId;
    private String compType;
    private String nameKor;
    private String nameEng;
    private String currentMonth;
    private String flag;
    private String secondaryValue;
    private String secondaryName;
    private String uom;
    private String uomKorean;
    private String etlJobName;
    private String isActive;
}
