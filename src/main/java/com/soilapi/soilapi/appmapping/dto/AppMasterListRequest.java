package com.soilapi.soilapi.appmapping.dto;

import lombok.Data;

@Data
public class AppMasterListRequest {
    private int pageNo;
    private int pageSize;
    private String orderBy = "AID";
    private String searchType = null;
    private String searchKeyword = null;
}
