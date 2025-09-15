package com.example.pedidos.domain.ports;

import com.example.pedidos.domain.model.User;

import io.jsonwebtoken.Claims;

public interface TokenProviderPort {
    String generarToken(User usuario);
    boolean validarToken(String token);
    String obtenerUsername(String token);
    
    Claims obtenerClaims(String token);
}
