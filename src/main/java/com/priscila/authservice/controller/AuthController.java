package com.priscila.authservice.controller;

import com.priscila.authservice.dto.*;
import com.priscila.authservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final  UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterRequestDTO dto) {
        UserResponseDTO response = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponseDTO> login(@RequestBody AuthLoginRequestDTO dto) {
        AuthLoginResponseDTO response = userService.login(dto);
        return ResponseEntity.ok(response);
    }
}
