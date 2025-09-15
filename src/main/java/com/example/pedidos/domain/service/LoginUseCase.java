package com.example.pedidos.domain.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.pedidos.domain.ports.TokenProviderPort;
import com.example.pedidos.domain.ports.UserRepositoryPort;

public class LoginUseCase {
    private final UserRepositoryPort userRepository;
    private final TokenProviderPort tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public LoginUseCase(UserRepositoryPort userRepository,
                        TokenProviderPort tokenProvider,
                        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String username, String password) {
        return userRepository.findByUsername(username)
            .filter(user -> passwordEncoder.matches(password, user.getPassword()))
            .map(tokenProvider::generarToken)
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
}
