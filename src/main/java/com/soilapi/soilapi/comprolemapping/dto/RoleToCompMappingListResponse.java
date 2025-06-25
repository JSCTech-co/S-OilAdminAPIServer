package com.soilapi.soilapi.comprolemapping.dto;

import java.util.List;

import lombok.Data;

@Data
public class RoleToCompMappingListResponse {
    private List<CompInfo> mappedList;
    private List<CompInfo> unmappedList;

    @Data
    public static class CompInfo {
        private String compId;
        private String compType;
        private String compName;
    }
}
