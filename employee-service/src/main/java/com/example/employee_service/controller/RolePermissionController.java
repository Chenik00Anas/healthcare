package com.example.employee_service.controller;

import com.example.employee_service.dto.*;
import com.example.employee_service.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolePermissionController {

    private final RoleService roleService;
    private final PermissionService permissionService;

    public RolePermissionController(RoleService roleService, PermissionService permissionService) {
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        RoleDTO role = roleService.getRoleById(id);
        return role != null ? 
               new ResponseEntity<>(role, HttpStatus.OK) :
               new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        RoleDTO createdRole = roleService.createRole(roleDTO);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @PostMapping("/{roleId}/permissions/{permissionId}")
    public ResponseEntity<RoleDTO> assignPermissionToRole(
            @PathVariable Long roleId, 
            @PathVariable Long permissionId) {
        RoleDTO updatedRole = roleService.assignPermissionToRole(roleId, permissionId);
        return updatedRole != null ? 
               new ResponseEntity<>(updatedRole, HttpStatus.OK) :
               new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{roleId}/permissions")
    public ResponseEntity<List<PermissionDTO>> getPermissionsForRole(@PathVariable Long roleId) {
        List<PermissionDTO> permissions = roleService.getPermissionsForRole(roleId);
        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<PermissionDTO>> getAllPermissions() {
        List<PermissionDTO> permissions = permissionService.getAllPermissions();
        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }

    @PostMapping("/permissions")
    public ResponseEntity<PermissionDTO> createPermission(@RequestBody PermissionDTO permissionDTO) {
        PermissionDTO createdPermission = permissionService.createPermission(permissionDTO);
        return new ResponseEntity<>(createdPermission, HttpStatus.CREATED);
    }
}