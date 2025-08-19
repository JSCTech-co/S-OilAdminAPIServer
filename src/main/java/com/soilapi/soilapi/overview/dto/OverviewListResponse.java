package com.soilapi.soilapi.overview.dto;

import java.util.List;

import lombok.Data;

@Data
public class OverviewListResponse {
    private int roleId;
    private String roleName;
    private List<widgetInfo> widgets;
    private List<widgetInfo> indices;

    @Data
    public static class widgetInfo{
        private int widgetOrder;
        private int cid;
        private String compId;
        private String compName;
        private String compNameKorean;
    }
}
