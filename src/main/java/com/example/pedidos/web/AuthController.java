package com.example.pedidos.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pedidos.application.dto.LoginRequest;
import com.example.pedidos.application.dto.LoginResponse;
import com.example.pedidos.application.dto.RegisterRequest;
import com.example.pedidos.application.dto.RegisterResponse;
import com.example.pedidos.domain.service.LoginUseCase;
import com.example.pedidos.domain.service.RegisterUserUseCase;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final LoginUseCase loginUseCase;
    private final RegisterUserUseCase registerUserUseCase;

    public AuthController(LoginUseCase loginUseCase, RegisterUserUseCase registerUserUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        String token = loginUseCase.login(request.getUsername(), request.getPassword());
        return new LoginResponse(token);
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        registerUserUseCase.register(request.getUsername(),
                                    request.getPassword(),
                                    request.getRol());
        return new RegisterResponse("Usuario registrado exitosamente");
    }
    
    @PostMapping("/test")
    public String test() {
        return "Test endpoint working!";
    }
}
