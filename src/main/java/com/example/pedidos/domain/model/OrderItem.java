package com.example.pedidos.domain.model;

import java.math.BigDecimal;

public class OrderItem {

    private Long id;
    private Long productId;   // ID del producto referenciado
    private int quantity;     // Cantidad pedida
    private BigDecimal price;
    private Order order;

    // ==== CONSTRUCTORES ====
    protected OrderItem() {} // JPA

    public OrderItem(Long productId, int quantity, BigDecimal price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    // ==== MÉTODOS DE DOMINIO ====
    public BigDecimal getSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    // ==== GETTERS & SETTERS ====
    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
    public Order getOrder() { return order; }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
