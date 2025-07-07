package com.soilapi.soilapi.appmapping.dto;

import java.util.List;

import lombok.Data;

@Data
public class AppToCompMappingListResponse {
    private List<AppToCompInfo> mappedList;
    private List<AppToCompInfo> unmappedList;
    
    @Data
    public static class AppToCompInfo{
        private int cid;
        private String compId;
        private String compType;
        private String nameKor;
        private String nameEng;
        private String mappingType;
    }
}
