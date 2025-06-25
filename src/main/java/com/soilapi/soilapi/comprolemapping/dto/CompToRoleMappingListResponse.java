package com.soilapi.soilapi.comprolemapping.dto;

import java.util.List;

import lombok.Data;

@Data
public class CompToRoleMappingListResponse {
    private List<RoleInfo> mappedList;
    private List<RoleInfo> unmappedList;

    @Data
    public static class RoleInfo {
        private int roleId;
        private String roleName;
    }
}
