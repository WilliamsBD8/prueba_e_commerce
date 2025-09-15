package com.example.pedidos.domain.ports;

import java.util.Optional;

import com.example.pedidos.domain.model.User;

public interface UserRepositoryPort {
    Optional<User> findByUsername(String username);
    User save(User usuario);
}
