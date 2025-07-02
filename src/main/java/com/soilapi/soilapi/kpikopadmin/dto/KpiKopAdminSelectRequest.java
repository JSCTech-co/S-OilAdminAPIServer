package com.soilapi.soilapi.kpikopadmin.dto;

import lombok.Data;

@Data
public class KpiKopAdminSelectRequest {
    private int pageNo;
    private int pageSize;
    private String orderBy = "compId";
    private String searchType;
    private String searchKeyword;
    private String isActive;
}
