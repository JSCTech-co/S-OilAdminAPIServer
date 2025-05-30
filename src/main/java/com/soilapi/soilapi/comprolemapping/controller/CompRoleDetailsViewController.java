package com.soilapi.soilapi.comprolemapping.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soilapi.soilapi.comprolemapping.entity.CompRoleDetailsView;
import com.soilapi.soilapi.comprolemapping.repository.CompRoleDetailsViewRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CompRoleDetailsViewController {
    private final CompRoleDetailsViewRepository crdvRepo;

    @GetMapping("/roles/getAllList")
    public List<CompRoleDetailsView> getAllRoles(){
        return crdvRepo.findAll();
    }

    @GetMapping("/roles/getByRoles")
    public List<CompRoleDetailsView> getByRoles(@RequestParam String roleName){
        return crdvRepo.findByRoleNameContainingIgnoreCaseOrderByCompIdAsc(roleName);
    }

    @GetMapping("/roles/getByComp")
    public List<CompRoleDetailsView> getByComp(@RequestParam String compName){
        return crdvRepo.findByCompNameContainingIgnoreCaseOrderByRoleIdAsc(compName);
    }

}
