package com.soilapi.soilapi.comprolemapping.dto;

import lombok.Data;

@Data
public class CompToRoleListRequest {
    private int pageNo;
    private int pageSize;
    private String orderBy = "compId";
    private String searchType;
    private String searchKeyword;
}
