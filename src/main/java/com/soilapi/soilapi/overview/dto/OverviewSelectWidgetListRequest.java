package com.soilapi.soilapi.overview.dto;

import lombok.Data;

@Data
public class OverviewSelectWidgetListRequest {
    int roleId;
    String orderBy = "Name_KOR";
}
