package com.soilapi.soilapi.reportadmin.dto;

import lombok.Data;

@Data
public class ReportAdminSelectResponse {
    private String compId;
    private String compName;
    private String compNameKorean;
    private String sourceSys;
    private String sourceSysName;
    private String ownerDept;
    private String hyperLink;
    private char isActive;
    private String etlJobName;
    private int compSeq;
}
