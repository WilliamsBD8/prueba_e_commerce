package com.example.pedidos.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.pedidos.domain.ports.TokenProviderPort;
import com.example.pedidos.domain.ports.UserRepositoryPort;
import com.example.pedidos.domain.service.LoginUseCase;
import com.example.pedidos.domain.service.RegisterUserUseCase;

@Configuration
public class UseCaseConfig {
    @Bean
    public LoginUseCase loginUseCase(UserRepositoryPort userRepository,
                                     TokenProviderPort tokenProvider,
                                     PasswordEncoder passwordEncoder) {
        return new LoginUseCase(userRepository, tokenProvider, passwordEncoder);
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(UserRepositoryPort userRepository,
                                                   PasswordEncoder passwordEncoder) {
        return new RegisterUserUseCase(userRepository, passwordEncoder);
    }
}
