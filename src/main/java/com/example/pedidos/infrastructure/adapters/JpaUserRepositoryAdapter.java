package com.example.pedidos.infrastructure.adapters;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.pedidos.domain.model.User;
import com.example.pedidos.domain.ports.UserRepositoryPort;
import com.example.pedidos.infrastructure.entity.UserEntity;
import com.example.pedidos.infrastructure.repository.SpringDataUserRepository;

@Component
public class JpaUserRepositoryAdapter implements UserRepositoryPort{
    private final SpringDataUserRepository repository;

    public JpaUserRepositoryAdapter(SpringDataUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username)
                         .map(this::toDomain);
    }

    private User toDomain(UserEntity entity) {
        User user = new User();
        user.setUsername(entity.getUsername());
        user.setPassword(entity.getPassword());
        user.setRol(entity.getRol());
        return user;
    }

    @Override
    public User save(User usuario) {
        UserEntity entity = new UserEntity();
        entity.setUsername(usuario.getUsername());
        entity.setPassword(usuario.getPassword());
        entity.setRol(usuario.getRol());

        UserEntity saved = repository.save(entity);
        User user = new User();
        user.setUsername(saved.getUsername());
        user.setPassword(saved.getPassword());
        user.setRol(saved.getRol());
        return user;
    }
}
