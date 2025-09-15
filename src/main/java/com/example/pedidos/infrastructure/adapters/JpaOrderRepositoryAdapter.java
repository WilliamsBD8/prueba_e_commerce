package com.example.pedidos.infrastructure.adapters;

import java.util.List;
import java.util.Optional;
// import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.pedidos.domain.model.Order;
// import com.example.pedidos.domain.model.OrderItem;
import com.example.pedidos.domain.ports.OrderRepositoryPort;
import com.example.pedidos.infrastructure.entity.OrderEntity;
import com.example.pedidos.infrastructure.repository.SpringDataOrderRepository;

@Component
public class JpaOrderRepositoryAdapter implements OrderRepositoryPort {
    private final SpringDataOrderRepository repository;

    public JpaOrderRepositoryAdapter(SpringDataOrderRepository repository) {
        this.repository = repository;
    }

     // Método para guardar entidad directamente
     public OrderEntity saveEntity(OrderEntity entity) {
        // Cada OrderItemEntity debe conocer su OrderEntity padre
        entity.getItems().forEach(item -> item.setOrder(entity));
        return repository.save(entity);
    }

    @Override
    public Order save(Order order) {
        order.calculateTotal(); // Calcula total antes de guardar

        // Convertir a entidad
        OrderEntity entity = OrderEntity.fromDomain(order);

        // Asegurar que cada item conoce su order
        entity.getItems().forEach(item -> item.setOrder(entity));

        // Guardar en BD
        OrderEntity savedEntity = repository.save(entity);

        // Convertir de vuelta a dominio con IDs generados
        return savedEntity.toDomain();
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll()
                         .stream()
                         .map(OrderEntity::toDomain)
                         .toList();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id)
                     .map(OrderEntity::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }



}
