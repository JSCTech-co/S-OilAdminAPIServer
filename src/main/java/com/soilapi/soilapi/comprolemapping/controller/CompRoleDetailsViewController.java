package com.soilapi.soilapi.comprolemapping.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soilapi.soilapi.comprolemapping.dto.CompToRoleListRequest;
import com.soilapi.soilapi.comprolemapping.dto.CompToRoleListResponse;
import com.soilapi.soilapi.comprolemapping.dto.CompToRoleMappingListResponse;
import com.soilapi.soilapi.comprolemapping.dto.CompToRoleModifyRequest;
import com.soilapi.soilapi.comprolemapping.dto.RoleToCompListRequest;
import com.soilapi.soilapi.comprolemapping.dto.RoleToCompListResponse;
import com.soilapi.soilapi.comprolemapping.dto.RoleToCompMappingListResponse;
import com.soilapi.soilapi.comprolemapping.dto.RoleToCompModifyRequest;
import com.soilapi.soilapi.comprolemapping.service.CompRoleDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CompRoleDetailsViewController {
    private final CompRoleDetailsService crdService;

    // ** Role To Comp **
    @GetMapping("/roles/RoleToCompRoleList")
    public Page<RoleToCompListResponse> roleToCompRoleList(@ModelAttribute RoleToCompListRequest dto){
        return crdService.selectRoleList(dto);
    }

    @GetMapping("roles/RoleToCompMappingList")
    public RoleToCompMappingListResponse roletoCompMappingList(@RequestParam int roleId){
        return crdService.selectRoleToCompMappingList(roleId);
    }

    @PostMapping("roles/RoleToCompModify")
    public ResponseEntity<String> roleToCompModify(@RequestBody RoleToCompModifyRequest dto){
        crdService.modifyRoleToComp(dto);
        return ResponseEntity.ok("RoleToComp 수정 완료.");
    }

    // ** Comp To Role **
    @GetMapping("roles/CompToRoleCompList")
    public Page<CompToRoleListResponse> compToRoleCompList(@ModelAttribute CompToRoleListRequest dto){
        return crdService.selectCompList(dto);
    }

    @GetMapping("roles/CompToRoleMappingList")
    public CompToRoleMappingListResponse compToRoleMappingList(@RequestParam String compId){
        return crdService.selectCompToRoleMappingList(compId);
    }

    @PostMapping("roles/CompToRoleModify")
    public ResponseEntity<String> compToRoleModify(@RequestBody CompToRoleModifyRequest dto){
        crdService.modifyCompToRole(dto);
        return ResponseEntity.ok("CompToRole 수정 완료.");
    }
}
