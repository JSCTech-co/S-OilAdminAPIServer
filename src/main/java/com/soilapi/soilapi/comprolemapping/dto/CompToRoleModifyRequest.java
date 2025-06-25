package com.soilapi.soilapi.comprolemapping.dto;

import java.util.List;

import lombok.Data;

@Data
public class CompToRoleModifyRequest {
    private String compId;
    private String compType;
    private List<Integer> addedRole;
    private List<Integer> removedRole;
}
