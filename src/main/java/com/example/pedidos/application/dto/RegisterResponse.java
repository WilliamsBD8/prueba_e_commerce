package com.example.pedidos.application.dto;

public class RegisterResponse {
    private String message;

    public RegisterResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
