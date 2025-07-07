package com.soilapi.soilapi.appmapping.dto;

import java.util.List;

import lombok.Data;

@Data
public class AppToCompModifyRequest {
    private Integer aid;
    private List<Integer> addList;
    private List<Integer> removeList;
}
