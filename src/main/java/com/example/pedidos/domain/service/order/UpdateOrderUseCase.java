package com.example.pedidos.domain.service.order;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.pedidos.domain.model.Order;
import com.example.pedidos.domain.model.OrderItem;
// import com.example.pedidos.domain.model.OrderStates;
import com.example.pedidos.domain.ports.OrderRepositoryPort;
// import com.example.pedidos.infrastructure.adapters.JpaOrderRepositoryAdapter;
// import com.example.pedidos.infrastructure.entity.OrderEntity;
import com.example.pedidos.domain.service.InventoryService;

@Service
public class UpdateOrderUseCase {
    private final OrderRepositoryPort orderRepository;
    private final InventoryService inventoryService;

    public UpdateOrderUseCase(OrderRepositoryPort orderRepository,
        InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.inventoryService = inventoryService;
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        // 1️⃣ Obtener el pedido existente
        Order existingOrder = orderRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Order no encontrada con id: " + id));

        // Buscar el item dentro del pedido
        for (OrderItem updatedItem : updatedOrder.getItems()) {
            OrderItem existingItem = existingOrder.getItems().stream()
                .filter(i -> i.getProductId().equals(updatedItem.getProductId()))
                .findFirst()
                .orElse(null);

            if (existingItem != null) {
                int diff = updatedItem.getQuantity() - existingItem.getQuantity();
                switch (existingOrder.getState()) {
                    case PENDING -> {
                        inventoryService.releaseStock(updatedItem.getProductId(), diff);
                    }
                    default -> {

                    }
                }
            }else{
                inventoryService.releaseStock(updatedItem.getProductId(), updatedItem.getQuantity());
            }
            
        }

        // 2️⃣ Validación de concurrencia (opcional: versión optimista)
        // if (!existingOrder.getVersion().equals(updatedOrder.getVersion())) {
        //     throw new IllegalStateException("Pedido modificado por otro usuario");
        // }

        // 3️⃣ Actualizar campos permitidos
        existingOrder.setCustomerId(updatedOrder.getCustomerId());
        existingOrder.setItems(updatedOrder.getItems());
        existingOrder.calculateTotal(); // recalcular total

        // 4️⃣ Avanzar estado si aplica
        try {
            // existingOrder.nextState();
        } catch (IllegalStateException e) {
            // No avanzar si ya está en estado terminal
        }

        // 5️⃣ Guardar con persistencia (maneja rollback automáticamente si falla)
        return orderRepository.save(existingOrder);
    }
}
