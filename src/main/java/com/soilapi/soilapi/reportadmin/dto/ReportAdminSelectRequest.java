package com.soilapi.soilapi.reportadmin.dto;

import lombok.Data;

@Data
public class ReportAdminSelectRequest {
    private int pageNo;
    private int pageSize;
    private String orderBy = "compId";
    private String searchType;
    private String searchKeyword;
    private String isActive;
}
