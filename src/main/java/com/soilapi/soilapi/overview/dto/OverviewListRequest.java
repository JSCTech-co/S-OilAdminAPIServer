package com.soilapi.soilapi.overview.dto;

import lombok.Data;

@Data
public class OverviewListRequest {
    private int pageNo;
    private int pageSize;
    private String orderBy = "roleName";
    private String searchType;
    private String searchKeyword;
}