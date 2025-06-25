package com.soilapi.soilapi.comprolemapping.dto;

import lombok.Data;

@Data
public class RoleToCompListRequest {
    private int pageNo;
    private int pageSize;
    private String orderBy = "roleName";
    private String searchType;
    private String searchKeyword;
}
