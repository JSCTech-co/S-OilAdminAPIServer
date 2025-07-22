package com.soilapi.soilapi.indiceadmin.dto;

import lombok.Data;

@Data
public class IndiceAdminSelectRequest {
    private int pageNo;
    private int pageSize;
    private String orderBy = "compId";
    private String searchType;
    private String searchKeyword;
    private String isActive;
}
