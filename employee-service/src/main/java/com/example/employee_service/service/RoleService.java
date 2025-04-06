package com.example.employee_service.service;
import com.example.employee_service.model.Permission;
import com.example.employee_service.dto.PermissionDTO;
import com.example.employee_service.dto.RoleDTO;
import com.example.employee_service.model.Role;
import com.example.employee_service.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionService permissionService;

    public RoleService(RoleRepository roleRepository, PermissionService permissionService) {
        this.roleRepository = roleRepository;
        this.permissionService = permissionService;
    }

    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getRoleName());
        Role savedRole = roleRepository.save(role);
        return mapEntityToDto(savedRole);
    }

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return mapEntityToDto(role);
    }

    public Role getRoleEntityById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        role.setName(roleDTO.getRoleName());
        Role updatedRole = roleRepository.save(role);
        return mapEntityToDto(updatedRole);
    }

    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        roleRepository.delete(role);
    }

    public RoleDTO assignPermissionToRole(Long roleId, Long permissionId) {
        Role role = getRoleEntityById(roleId);
        role.getPermissions().add(permissionService.getPermissionEntityById(permissionId));
        Role updatedRole = roleRepository.save(role);
        return mapEntityToDto(updatedRole);
    }

    private RoleDTO mapEntityToDto(Role entity) {
        RoleDTO dto = new RoleDTO();
        dto.setId(entity.getId());
        dto.setRoleName(entity.getName());
        dto.setPermissions(entity.getPermissions().stream()
                .map(p -> p.getName())
                .collect(Collectors.toList()));
        return dto;
    }
    public List<PermissionDTO> getPermissionsForRole(Long roleId) {
    Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new RuntimeException("Role not found"));
    return role.getPermissions().stream()
            .map(this::mapPermissionToDto)
            .collect(Collectors.toList());
}

private PermissionDTO mapPermissionToDto(Permission permission) {
    PermissionDTO dto = new PermissionDTO();
    dto.setId(permission.getId());
    dto.setPermissionName(permission.getName());
    return dto;
}
}