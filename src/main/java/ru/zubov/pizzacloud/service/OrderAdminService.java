package ru.zubov.pizzacloud.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.zubov.pizzacloud.repository.OrderRepository;

import java.util.Objects;

@Service
public class OrderAdminService {
    private final OrderRepository orderRepository;

    public OrderAdminService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    public OrderRepository orderRepository() {
        return orderRepository;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (OrderAdminService) obj;
        return Objects.equals(this.orderRepository, that.orderRepository);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderRepository);
    }

    @Override
    public String toString() {
        return "OrderAdminService[" +
                "orderRepository=" + orderRepository + ']';
    }

}
