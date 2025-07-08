package com.soilapi.soilapi.appmapping.dto;

import lombok.Data;

@Data
public class AppMasterInsertRequest {
    private String appName;
    private String appId;
    private String appType;
    private String qlikAppName;
}
