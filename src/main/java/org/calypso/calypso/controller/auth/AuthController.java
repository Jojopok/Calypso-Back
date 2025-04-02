package org.calypso.calypso.controller.auth;

import io.jsonwebtoken.Claims;
import org.calypso.calypso.dto.auth.UserDTO;
import org.calypso.calypso.dto.auth.UserLoginDTO;
import org.calypso.calypso.dto.auth.UserRegistrationDTO;
import org.calypso.calypso.mapper.auth.UserMapper;
import org.calypso.calypso.model.auth.Promo;
import org.calypso.calypso.model.auth.User;
import org.calypso.calypso.security.AuthenticationService;
import org.calypso.calypso.security.JwtService;
import org.calypso.calypso.service.auth.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;
    private UserController userController;
    private final JwtService jwtService;

    @Autowired
    public AuthController(UserService userService, AuthenticationService authenticationService, UserMapper userMapper, JwtService jwtService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        User user = userMapper.toEntity(userRegistrationDTO);
        User registeredUser = userService.registerUser(
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getOdysseyLink()
        );
        UserDTO userDTO = userMapper.toUserDTO(registeredUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody UserLoginDTO userLoginDTO) {
        // Authentifier l'utilisateur

        String token = authenticationService.authenticate(
                userLoginDTO.getEmail(),
                userLoginDTO.getPassword()
        );


        // Récupérer l'utilisateur complet depuis la base de données
        User user = userService.getUserByEmail(userLoginDTO.getEmail());

        // Convertir l'utilisateur en UserDTO avec les rôles sous forme de chaînes de caractères
        UserDTO userDTO = userMapper.toUserDTO(user);

        // Générer le token avec les détails de l'utilisateur
        String tokenWithDetails = jwtService.generateToken(user.getId(), user.getEmail(), user.getRoleIds());

        // Construire la réponse JSON avec le token et le UserDTO
        Map<String, Object> response = new HashMap<>();
        response.put("token", tokenWithDetails);  // Le token JWT
        response.put("user", userDTO);  // L'objet UserDTO avec les informations de l'utilisateur

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(response);
    }

    @GetMapping("/profil")
    public UserDTO getCurrentProfile(@RequestHeader("Authorization") String token) {
        // Extraire l'email du token JWT (Assume que le token est sous forme "Bearer <token>")
        String email = jwtService.extractEmailFromToken(token);

        // Récupérer l'utilisateur à partir de l'email
        User user = userService.getUserByEmail(email);

        // Convertir l'utilisateur en DTO en utilisant le SerMapper
        return userMapper.toUserDTO(user);
    }

    @Autowired
    public void setUserController(UserController userController) {
        this.userController = userController;
    }
}