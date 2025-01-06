package org.calypso.calypso.service.auth;

import org.calypso.calypso.mapper.auth.RoleMapper;
import org.calypso.calypso.model.auth.Role;
import org.calypso.calypso.model.auth.User;
import org.calypso.calypso.repository.auth.RoleRepository;
import org.calypso.calypso.repository.auth.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegisterUser_Success() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        String firstName = "John";
        String lastName = "Doe";
        String odysseyLink = "https://odyssey.example.com/john-doe";

        Role userRole = new Role();
        userRole.setRole("User");

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(roleRepository.findByRole("User")).thenReturn(userRole);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User result = userService.registerUser(email, password, firstName, lastName, odysseyLink);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getFirstName()).isEqualTo(firstName);
        assertThat(result.getLastName()).isEqualTo(lastName);
        assertThat(result.getPassword()).isEqualTo("encodedPassword");
        assertThat(result.getRoles()).contains(userRole);
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        // Arrange
        String email = "test@example.com";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> userService.registerUser(email, "password", "John", "Doe", "https://odyssey.example.com/john-doe"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Cet email est déjà utilisé");
    }

    @Test
    void testRegisterUser_RoleNotFound() {
        // Arrange
        String email = "test@example.com";

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(roleRepository.findByRole("User")).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> userService.registerUser(email, "password", "John", "Doe", "https://odyssey.example.com/john-doe"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Le rôle 'User' n'existe pas");
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        User user1 = new User();
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setEmail("user2@example.com");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        // Act
        List<User> users = userService.getAllUsers();

        // Assert
        assertThat(users).hasSize(2);
        assertThat(users.get(0).getEmail()).isEqualTo("user1@example.com");
        assertThat(users.get(1).getEmail()).isEqualTo("user2@example.com");
    }
}
