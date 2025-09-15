package com.example.pedidos.domain.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

import com.example.pedidos.domain.model.User;
import com.example.pedidos.domain.ports.UserRepositoryPort;

// @Service
public class RegisterUserUseCase {
    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCase(UserRepositoryPort userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(String username, String password, String rol) {
        // verificar si ya existe
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        // encriptar password
        String encodedPassword = passwordEncoder.encode(password);

        // crear usuario de dominio
        User nuevo = new User(username, encodedPassword, rol);

        // persistir
        userRepository.save(nuevo);
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(UserRepositoryPort userRepository,
                                                PasswordEncoder passwordEncoder) {
        return new RegisterUserUseCase(userRepository, passwordEncoder);
    }
}
