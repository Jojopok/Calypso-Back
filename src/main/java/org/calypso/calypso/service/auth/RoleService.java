package org.calypso.calypso.service.auth;

import org.calypso.calypso.model.auth.Role;
import org.calypso.calypso.repository.auth.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(String roleName, String color) {
        Role role = new Role();
        role.setRole(roleName);
        role.setColor(color);
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findByRole(roleName);
    }
}