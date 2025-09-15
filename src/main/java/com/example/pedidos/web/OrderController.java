package com.example.pedidos.web;

import java.util.List;
import com.example.pedidos.domain.service.order.UpdateOrderUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pedidos.domain.model.Order;
import com.example.pedidos.domain.model.OrderStates;
// import com.example.pedidos.domain.model.OrderItem;
// import com.example.pedidos.domain.model.OrderItem;
import com.example.pedidos.domain.service.order.CreateOrderUseCase;
import com.example.pedidos.domain.service.order.UpdateOrderBackStateUseCase;
import com.example.pedidos.domain.service.order.UpdateOrderStateUseCase;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final UpdateOrderUseCase updateOrderUseCase;
    private final CreateOrderUseCase createOrderUseCase;
    private final UpdateOrderStateUseCase updateOrderStateUseCase;
    private final UpdateOrderBackStateUseCase updateOrderBackStateUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase,
            UpdateOrderUseCase updateOrderUseCase,
            UpdateOrderStateUseCase updateOrderStateUseCase,
            UpdateOrderBackStateUseCase updateOrderBackStateUseCase){
        this.createOrderUseCase = createOrderUseCase;
        this.updateOrderUseCase = updateOrderUseCase;
        this.updateOrderStateUseCase = updateOrderStateUseCase;
        this.updateOrderBackStateUseCase = updateOrderBackStateUseCase;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(createOrderUseCase.createOrder(order));
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<Order> addItemToOrder(@PathVariable Long id, @RequestBody Order order) {
        return ResponseEntity.ok(updateOrderUseCase.updateOrder(id, order));
    }

    @PutMapping("/{id}/state")
    public ResponseEntity<Order> updateOrderStateUseCase(@PathVariable Long id, @RequestBody Order order) {
        order.setId(id);
        return ResponseEntity.ok(updateOrderStateUseCase.updateOrder(id, order));
    }

    @PutMapping("/{id}/back_state")
    public ResponseEntity<Order> updateOrderBackStateUseCase(@PathVariable Long id, @RequestBody Order order) {
        order.setId(id);
        return ResponseEntity.ok(updateOrderBackStateUseCase.updateOrder(id));
    }
}
