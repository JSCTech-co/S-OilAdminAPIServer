package com.soilapi.soilapi.overview.dto;

import lombok.Data;

@Data
public class OverviewSelectIndiceListRequest {
    int roleId;
    String orderBy = "Name_KOR";
}
