package com.soilapi.soilapi.indiceadmin.dto;

import lombok.Data;

@Data
public class IndiceAdminSelectResponse {
    private int cid;
    private String compId;
    private String compType;
    private String nameENG;
    private String nameKOR;
    private String currentMonth;
    private String flag;
    private String secondaryName;
    private String secondaryValue;
    private String uom;
    private String uomKorean;
    private String ETLJobName;
}
