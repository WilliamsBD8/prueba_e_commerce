package com.example.pedidos.infrastructure.entity;

import java.math.BigDecimal;

import com.example.pedidos.domain.model.OrderItem;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private int quantity;
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    // Constructores
    protected OrderItemEntity() {}

    public OrderItemEntity(Long productId, int quantity, BigDecimal price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    // Métodos de conversión
    public static OrderItemEntity fromDomain(OrderItem item) {
        OrderItemEntity entity = new OrderItemEntity(
            item.getProductId(),
            item.getQuantity(),
            item.getPrice()
        );
        entity.setId(item.getId());
        return entity;
    }

    public OrderItem toDomain() {
        OrderItem item = new OrderItem(productId, quantity, price);
        item.setId(id);
        return item;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public OrderEntity getOrder() { return order; }
    public void setOrder(OrderEntity order) { this.order = order; }
}
