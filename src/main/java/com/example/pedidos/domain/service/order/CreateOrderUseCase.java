package com.example.pedidos.domain.service.order;

// import java.util.Map;
// import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.pedidos.domain.events.OrderStateChangedEvent;
import com.example.pedidos.domain.model.Order;
import com.example.pedidos.domain.model.OrderItem;
import com.example.pedidos.domain.model.OrderStates;
import com.example.pedidos.domain.ports.OrderRepositoryPort;
import com.example.pedidos.domain.service.InventoryService;
import com.example.pedidos.infrastructure.adapters.JpaOrderRepositoryAdapter;
// import com.example.pedidos.infrastructure.adapters.JpaOrderRepositoryAdapter;
// import com.example.pedidos.infrastructure.entity.OrderEntity;
import com.example.pedidos.infrastructure.entity.OrderEntity;

@Service
public class CreateOrderUseCase {
    private final OrderRepositoryPort orderRepository;
    private final InventoryService inventoryService;

    public CreateOrderUseCase(OrderRepositoryPort orderRepository,
                              InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.inventoryService = inventoryService;
    }

    @Transactional
    public Order createOrder(Order order) {
        // 1️⃣ Calcular total del pedido
        order.calculateTotal();
        order.setState(OrderStates.PENDING);

        // Reservar inventario
        // Map<Long, Integer> itemsToReserve = order.getItems().stream()
        //     .collect(Collectors.toMap(OrderItem::getProductId, OrderItem::getQuantity));

        try {
            for (OrderItem item : order.getItems()) {
                inventoryService.releaseStock(item.getProductId(), item.getQuantity());
            }

        } catch (Exception e) {
            // 4️⃣ Rollback: liberar stock si algo falla
            order.setState(OrderStates.FAILED);
            throw e;
        }

        OrderEntity entity = OrderEntity.fromDomain(order);
        OrderEntity savedEntity = ((JpaOrderRepositoryAdapter) orderRepository).saveEntity(entity);

        return savedEntity.toDomain();
    }

}
