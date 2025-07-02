package com.soilapi.soilapi.comprolemapping.dto;

import lombok.Data;

@Data
public class CompToRoleListResponse {
    private String compId;
    private String compType;
    private String compName;
    private String compNameKorean;
    private int roleCount;
}
