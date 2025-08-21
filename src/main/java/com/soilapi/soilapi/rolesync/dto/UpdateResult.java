package com.soilapi.soilapi.rolesync.dto;

import java.util.List;

import lombok.Data;

@Data
public class UpdateResult {
    private int requestedCount;
    private int uniqueRoleCount;
    private int updatedRows;
    private List<String> notMatchedRoles;

    private int insertedRows;             
    private List<String> insertedRoles; 
}
