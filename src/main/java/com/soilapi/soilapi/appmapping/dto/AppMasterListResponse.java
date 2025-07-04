package com.soilapi.soilapi.appmapping.dto;

import lombok.Data;

@Data
public class AppMasterListResponse {
    private Integer aid;
    private String appName;
    private String appId;
    private String appType;
    private Integer compCount;
}
