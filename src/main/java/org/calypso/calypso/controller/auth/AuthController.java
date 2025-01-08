package org.calypso.calypso.controller.auth;

import org.calypso.calypso.dto.auth.UserDTO;
import org.calypso.calypso.dto.auth.UserLoginDTO;
import org.calypso.calypso.dto.auth.UserRegistrationDTO;
import org.calypso.calypso.mapper.auth.UserMapper;
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
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody UserLoginDTO userLoginDTO) {
        // Authentification et récupération de l'utilisateur
        String token = authenticationService.authenticate(
                userLoginDTO.getEmail(),
                userLoginDTO.getPassword()
        );

        // Récupérer l'utilisateur complet depuis la base de données
        User user = userService.getUserByEmail(userLoginDTO.getEmail());

        // Récupérer les IDs des rôles de l'utilisateur
        Set<Long> roleIds = user.getRoleIds();

        // Générer le token avec l'ID de l'utilisateur, l'email et les IDs des rôles
        String tokenWithDetails = jwtService.generateTokenWithUserId(
                user.getId(), user.getEmail(), roleIds
        );

        // Construire la réponse JSON avec le token
        Map<String, String> response = new HashMap<>();
        response.put("token", tokenWithDetails);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(response);
    }





    @Autowired
    public void setUserController(UserController userController) {
        this.userController = userController;
    }
}