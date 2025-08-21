package com.soilapi.soilapi.roleadmin.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soilapi.soilapi.roleadmin.dto.RoleAdminListRequest;
import com.soilapi.soilapi.roleadmin.dto.RoleAdminListResponse;
import com.soilapi.soilapi.roleadmin.service.RoleAdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/roleadmin")
@RequiredArgsConstructor
public class RoleAdminController {
    
    private final RoleAdminService raService;
    
    @GetMapping("/select")
    public Page<RoleAdminListResponse> select(@ModelAttribute RoleAdminListRequest dto){
        return raService.selectRoleAdmin(dto);
    }
}
