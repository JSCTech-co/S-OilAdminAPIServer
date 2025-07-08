package com.soilapi.soilapi.appmapping.dto;

import lombok.Data;

@Data
public class AppMasterUpdateRequest {
    private Integer aid;
    private String appName;
    private String appId;
    private String appType;
    private String qlikAppName;
}
