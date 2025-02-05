package org.calypso.calypso.controller.auth;

import org.calypso.calypso.dto.auth.UserDTO;
import org.calypso.calypso.mapper.auth.UserMapper;
import org.calypso.calypso.model.auth.User;
import org.calypso.calypso.service.auth.RoleService;
import org.calypso.calypso.service.auth.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers().stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        // Récupérer l'utilisateur à partir de l'ID
        User user = userService.getUserById(id);

        // Vérifier si l'utilisateur existe
        if (user == null) {
            return ResponseEntity.notFound().build(); // Retourner 404 si l'utilisateur n'existe pas
        }

        // Mettre à jour les informations de l'utilisateur
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());
        user.setOdysseyLink(userDTO.getOdysseyLink());

        // Sauvegarder les modifications dans la base de données
        User updatedUser = userService.updateUser(user);

        // Mapper l'utilisateur mis à jour en DTO
        UserDTO updatedUserDTO = userMapper.toUserDTO(updatedUser);

        // Retourner la réponse avec l'utilisateur mis à jour
        return ResponseEntity.ok(updatedUserDTO);
    }
}