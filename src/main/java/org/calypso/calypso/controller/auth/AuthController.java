package org.calypso.calypso.controller.auth;

import org.calypso.calypso.dto.auth.UserDTO;
import org.calypso.calypso.dto.auth.UserLoginDTO;
import org.calypso.calypso.dto.auth.UserRegistrationDTO;
import org.calypso.calypso.mapper.auth.UserMapper;
import org.calypso.calypso.model.auth.User;
import org.calypso.calypso.security.AuthenticationService;
import org.calypso.calypso.service.auth.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    public AuthController(UserService userService, AuthenticationService authenticationService, UserMapper userMapper) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
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
    public ResponseEntity<String> authenticate(@RequestBody UserLoginDTO userLoginDTO) {
        String token = authenticationService.authenticate(
                userLoginDTO.getEmail(),
                userLoginDTO.getPassword()
        );
        return ResponseEntity.ok(token);
    }
}