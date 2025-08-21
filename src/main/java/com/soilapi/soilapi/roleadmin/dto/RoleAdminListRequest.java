package com.soilapi.soilapi.roleadmin.dto;

import lombok.Data;

@Data
public class RoleAdminListRequest{
    private int pageNo;
    private int pageSize;
    private String orderBy = "roleName";
    private String searchType;
    private String searchKeyword;
}
