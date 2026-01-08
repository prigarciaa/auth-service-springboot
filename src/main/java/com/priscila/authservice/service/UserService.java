package com.priscila.authservice.service;

import com.priscila.authservice.dto.AuthLoginRequestDTO;
import com.priscila.authservice.dto.AuthLoginResponseDTO;
import com.priscila.authservice.dto.UserRegisterRequestDTO;
import com.priscila.authservice.dto.UserResponseDTO;
import com.priscila.authservice.model.User;
import com.priscila.authservice.repository.UserRepository;
import com.priscila.authservice.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    // LOGIN
    public AuthLoginResponseDTO login(AuthLoginRequestDTO dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthLoginResponseDTO(token);
    }

    // REGISTER
    public UserResponseDTO register(UserRegisterRequestDTO dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User saved = userRepository.save(user);

        return new UserResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getEmail()
        );
    }
}