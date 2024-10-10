package org.calypso.calypso.controller.auth;

import org.calypso.calypso.dto.auth.RoleDTO;
import org.calypso.calypso.model.auth.Role;
import org.calypso.calypso.service.auth.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        Role role = roleService.createRole(roleDTO.getRole());
        roleDTO.setId(role.getId());
        return ResponseEntity.ok(roleDTO);
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        List<RoleDTO> roleDTOs = roles.stream()
                .map(role -> {
                    RoleDTO dto = new RoleDTO();
                    dto.setId(role.getId());
                    dto.setRole(role.getRole());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(roleDTOs);
    }
}