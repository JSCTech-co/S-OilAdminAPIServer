package com.soilapi.soilapi.kpikopfilteradmin.dto;

import lombok.Data;

@Data
public class KpiKopFilterAdminUpdateRequest {
    private Integer filterId;            
    private String filterType;           
    private String filterName;        
    private String fieldName;           
    private String filterLabel;           
    private String widgetObjectId;       
    private String filterObjId;           
    private String filterQlikId;          
    private Integer filterSequence;       
    private String pageName;              
}
