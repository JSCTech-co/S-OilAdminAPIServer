package com.soilapi.soilapi.rolesync.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soilapi.soilapi.rolesync.dto.RoleItem;
import com.soilapi.soilapi.rolesync.dto.UpdateResult;
import com.soilapi.soilapi.rolesync.service.UpdateSharePointIdService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/rolesync")
@AllArgsConstructor
public class RoleSyncController {
    private final UpdateSharePointIdService service;

    @PostMapping(value = "/spidsync")
    public UpdateResult syncSharePointIds(
            @RequestParam(defaultValue = "false") boolean dryRun,      // true이면 미반영 미리보기
            @RequestParam(defaultValue = "true") boolean ignoreCase,  // true이면 RoleName 대소문자 무시
            @RequestParam(defaultValue = "true") boolean insertMissing,  
            @RequestBody List<RoleItem> items                          // JSON 배열 본문
    ) {
        return service.updateFromList(items, dryRun, ignoreCase, insertMissing);
    }

    @GetMapping("/xmlparse")
    public ResponseEntity<String> xmlParse(){
        service.xmlparse();
        return ResponseEntity.ok("Xml Parse 완료");
    }
}
