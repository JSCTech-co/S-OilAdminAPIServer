package com.soilapi.soilapi.comprolemapping.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class RoleToCompModifyRequest {
    private int roleId;
    private Map<String, List<String>> addedList;
    private Map<String, List<String>> removedList;
}
