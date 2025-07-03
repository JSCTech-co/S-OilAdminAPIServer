package com.soilapi.soilapi.kpikopfilteradmin.dto;

import lombok.Data;

@Data
public class KpiKopFilterAdminInsertRequest {
    private String compId;                  
    private String filterType;               
    private String filterName;               
    private String fieldName;                
    private String filterLabel;          
    private String widgetObjectId;           
    private String filterObjID;             
    private Integer filterSequence;        
    private String pageName;               
}
