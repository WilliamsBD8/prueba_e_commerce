package com.example.pedidos.domain.service.product;

import org.springframework.stereotype.Service;

import com.example.pedidos.domain.ports.ProductRepositoryPort;

@Service
public class DeleteUseCase {
    private final ProductRepositoryPort productRepository;

    public DeleteUseCase(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
