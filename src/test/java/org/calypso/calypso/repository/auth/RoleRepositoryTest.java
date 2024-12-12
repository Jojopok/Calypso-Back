package org.calypso.calypso.repository.auth;

import org.calypso.calypso.model.auth.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testSaveRole() {
        // Arrange
        Role role = new Role();
        role.setRole("ROLE_ADMIN");

        // Act
        Role savedRole = roleRepository.save(role);

        // Assert
        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getId()).isNotNull();
        assertThat(savedRole.getRole()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    void testFindById() {
        // Arrange
        Role role = new Role();
        role.setRole("ROLE_USER");
        Role savedRole = roleRepository.save(role);

        // Act
        Optional<Role> foundRole = roleRepository.findById(savedRole.getId());

        // Assert
        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getRole()).isEqualTo("ROLE_USER");
    }

    @Test
    void testFindByRole() {
        // Arrange
        Role role = new Role();
        role.setRole("ROLE_MANAGER");
        roleRepository.save(role);

        // Act
        Role foundRole = roleRepository.findByRole("ROLE_MANAGER");

        // Assert
        assertThat(foundRole).isNotNull();
        assertThat(foundRole.getRole()).isEqualTo("ROLE_MANAGER");
    }
}
