package com.soilapi.soilapi.reportadmin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportAdminInsertRequest {
    private String compId;
    private String compName;
    private String compNameKorean;
    private String sourceSys;
    private String sourceSysName;
    private String ownerDept;
    private String hyperLink;
    private String etlJobName;
    private String isActive;
    private String createdBy;
}
