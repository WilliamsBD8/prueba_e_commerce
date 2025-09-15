package com.example.pedidos.domain.service.order;

// import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.pedidos.domain.events.OrderStateChangedEvent;
import com.example.pedidos.domain.model.Order;
import com.example.pedidos.domain.model.OrderStates;
import com.example.pedidos.domain.ports.OrderEventPublisherPort;
import com.example.pedidos.domain.ports.OrderRepositoryPort;
import com.example.pedidos.domain.service.InventoryService;

@Service
public class UpdateOrderStateUseCase {
    private final OrderRepositoryPort orderRepository;
    private final InventoryService inventoryService;
    private final OrderEventPublisherPort orderEventPublisher; 

    public UpdateOrderStateUseCase(OrderRepositoryPort orderRepository, InventoryService inventoryService, OrderEventPublisherPort orderEventPublisher) {
        this.orderRepository = orderRepository;
        this.inventoryService = inventoryService;
        this.orderEventPublisher = orderEventPublisher;
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        // 1️⃣ Obtener el pedido existente
        Order existingOrder = orderRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Order no encontrada con id: " + id));

        OrderStates previousState = existingOrder.getState();

        // 2️⃣ Validación de concurrencia (opcional: versión optimista)
        // if (!existingOrder.getVersion().equals(updatedOrder.getVersion())) {
        //     throw new IllegalStateException("Pedido modificado por otro usuario");
        // }

        // 4️⃣ Avanzar estado si aplica
        try {
            existingOrder.nextState();

            switch (existingOrder.getState()) {
                case CONFIRMED -> {
                    // Confirmado: descontar stock y liberar reservas
                    existingOrder.getItems().forEach(item -> inventoryService.decreaseStock(item.getProductId(), item.getQuantity()));
                }
                case CANCELLED, FAILED -> {
                    // Cancelado o fallido: liberar reservas
                    existingOrder.getItems().forEach(item -> inventoryService.releaseStock(item.getProductId(), item.getQuantity()));
                }
                default -> {
                    // Otros estados no requieren acción sobre inventario
                }
            }

            if (previousState != existingOrder.getState()) {
                orderEventPublisher.publishOrderStateChangedEvent(
                    new OrderStateChangedEvent(existingOrder, previousState, existingOrder.getState())
                );
            }

            
        } catch (IllegalStateException e) {
            // No avanzar si ya está en estado terminal
        }

        // 5️⃣ Guardar con persistencia (maneja rollback automáticamente si falla)
        return orderRepository.save(existingOrder);
    }
}
