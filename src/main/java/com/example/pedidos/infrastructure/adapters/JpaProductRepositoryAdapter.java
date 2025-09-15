package com.example.pedidos.infrastructure.adapters;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
// import org.springframework.stereotype.Service;

import com.example.pedidos.domain.model.Product;
import com.example.pedidos.domain.ports.ProductRepositoryPort;
import com.example.pedidos.infrastructure.entity.ProductEntity;
import com.example.pedidos.infrastructure.repository.SpringDataProductRepository;

@Component
public class JpaProductRepositoryAdapter implements ProductRepositoryPort{
    private final SpringDataProductRepository repository;

    public JpaProductRepositoryAdapter(SpringDataProductRepository repository) {
        this.repository = repository;
    }

    private Product toDomain(ProductEntity entity) {
        return new Product(entity.getId(), entity.getName(), entity.getPrice(), entity.getStock(), entity.getState());
    }

    private ProductEntity toEntity(Product domain) {
        ProductEntity entity = new ProductEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setPrice(domain.getPrice());
        entity.setState(domain.getState());
        entity.setStock(domain.getStock());
        return entity;
    }

    @Override
    public Product save(Product product) {
        ProductEntity saved = repository.save(toEntity(product));
        return toDomain(saved);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
