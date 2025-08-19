package com.soilapi.soilapi.overview.dto;

import lombok.Data;

@Data
public class OverviewListFlatResponse {
    private int roleId;
    private String roleName;
    private String widgetType;
    private int widgetOrder;
    private int cid;
    private String compId;
    private String compName;
    private String compNameKorean;
}