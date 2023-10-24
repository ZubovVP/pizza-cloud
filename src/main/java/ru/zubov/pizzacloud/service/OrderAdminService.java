package ru.zubov.pizzacloud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.zubov.pizzacloud.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public record OrderAdminService(OrderRepository orderRepository) {
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }
}
