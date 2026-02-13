package com.zerowastehub.backend.controller;

import com.zerowastehub.backend.dto.UserRoleDto;
import com.zerowastehub.backend.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ADMIN/user-role")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;
    @PostMapping("/add")
    public ResponseEntity<UserRoleDto> create(@RequestBody UserRoleDto dto) {
        return ResponseEntity.ok(userRoleService.createRole(dto));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<UserRoleDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(userRoleService.getRoleById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserRoleDto>> getAll() {
        return ResponseEntity.ok(userRoleService.getAllRoles());
    }

    @PutMapping("/update-by-id/{id}")
    public ResponseEntity<UserRoleDto> update(
            @PathVariable Integer id,
            @RequestBody UserRoleDto dto) {
        return ResponseEntity.ok(userRoleService.updateRole(id, dto));
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        userRoleService.deleteRole(id);
        return ResponseEntity.ok("Role deleted successfully");
    }
}
