package com.soilapi.soilapi.comprolemapping.dto;

import lombok.Data;

@Data
public class RoleToCompListResponse {
    private int roleId;
    private String roleName;
    private int componentCount;

}
