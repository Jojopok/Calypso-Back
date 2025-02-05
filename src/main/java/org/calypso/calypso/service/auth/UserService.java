package org.calypso.calypso.service.auth;

import jakarta.persistence.EntityNotFoundException;
import org.calypso.calypso.mapper.auth.RoleMapper;
import org.calypso.calypso.model.auth.Role;
import org.calypso.calypso.model.auth.User;
import org.calypso.calypso.repository.auth.RoleRepository;
import org.calypso.calypso.repository.auth.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleMapper roleMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, RoleMapper roleMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleMapper = roleMapper;
    }

    public User registerUser(String email, String password, String firstName, String lastName, String odysseyLink) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(password));
        user.setOdysseyLink(odysseyLink);

        Role userRole = roleRepository.findByRole("User");
        if (userRole == null) {
            throw new RuntimeException("Le rôle 'User' n'existe pas");
        }
        user.setRoles(Set.of(userRole));

        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Long getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email))
                .getId();
    }

    // Mettre à jour un utilisateur
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}