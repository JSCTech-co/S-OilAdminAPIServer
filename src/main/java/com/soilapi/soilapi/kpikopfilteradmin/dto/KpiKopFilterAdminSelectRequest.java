package com.soilapi.soilapi.kpikopfilteradmin.dto;

import lombok.Data;

@Data
public class KpiKopFilterAdminSelectRequest {
    private int pageNo;
    private int pageSize;
    private String orderBy = "compId";
    private String searchType;
    private String searchKeyword;
}
