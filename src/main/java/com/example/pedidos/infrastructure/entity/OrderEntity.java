package com.example.pedidos.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.pedidos.domain.model.Order;
import com.example.pedidos.domain.model.OrderStates;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    @Enumerated(EnumType.STRING)
    private OrderStates state;

    private BigDecimal total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items = new ArrayList<>();

    // @Version
    // private Long version;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructores
    protected OrderEntity() {}

    public OrderEntity(Long customerId, OrderStates state, BigDecimal total) {
        this.customerId = customerId;
        this.state = state;
        this.total = total;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Métodos de conversión
    public static OrderEntity fromDomain(Order order) {
        OrderEntity entity = new OrderEntity(
            order.getCustomerId(),
            order.getState(),
            order.getTotal()
        );
        entity.setId(order.getId());
        entity.setCreatedAt(order.getCreatedAt());
        entity.setUpdatedAt(order.getUpdatedAt());
    
        // Convertir OrderItems
        order.getItems().forEach(item -> {
            OrderItemEntity itemEntity = OrderItemEntity.fromDomain(item);
            itemEntity.setOrder(entity);
            entity.getItems().add(itemEntity);
        });
    
        return entity;
    }

    public Order toDomain() {
        Order order = new Order(this.customerId, new ArrayList<>());
        order.setId(this.id);
        order.setState(this.state);
        order.setTotal(this.total);
        // order.setVersion(this.version);
        order.setCreatedAt(this.createdAt);
        order.setUpdatedAt(this.updatedAt);
        
        // Convertir OrderItemEntities
        this.items.forEach(itemEntity -> {
            order.getItems().add(itemEntity.toDomain());
        });
        
        return order;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    
    public OrderStates getStatus() { return state; }
    public void setStatus(OrderStates status) { this.state = status; }
    
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    
    public List<OrderItemEntity> getItems() { return items; }
    public void setItems(List<OrderItemEntity> items) { this.items = items; }
    
    // public Long getVersion() { return version; }
    // public void setVersion(Long version) { this.version = version; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
