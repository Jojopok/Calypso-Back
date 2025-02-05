package org.calypso.calypso.mapper.auth;

import org.calypso.calypso.dto.auth.RoleDTO;
import org.calypso.calypso.model.auth.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role toEntity(RoleDTO dto) {
        Role role = new Role();
        role.setRole(dto.getRole());
        role.setColor(dto.getColor());
        return role;
    }

    public RoleDTO toDto(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setRole(role.getRole());
        dto.setColor(role.getColor());
        return dto;
    }
}