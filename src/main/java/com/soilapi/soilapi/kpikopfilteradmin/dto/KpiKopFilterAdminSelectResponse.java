package com.soilapi.soilapi.kpikopfilteradmin.dto;

import java.util.List;

import lombok.Data;

@Data
public class KpiKopFilterAdminSelectResponse {
    private String compId;
    private String compName;
    private String compNameKorean;
    private String compType;
    private Integer filterCount;
    private List<FilterInfo> filters;

    @Data
    public static class FilterInfo{
        private Integer filterId;
        private String filterName;
        private String filterLabel;
        private String filterType;
        private Integer filterSequence;
        private String widgetObjectId;
        private String filterObjId;
        private Integer filterVariableOptionId;
        private String pageName;
        private String filterQlikId;
        private String fieldName;
    }
}
