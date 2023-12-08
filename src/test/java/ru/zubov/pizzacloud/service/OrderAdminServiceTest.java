package ru.zubov.pizzacloud.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.zubov.pizzacloud.repository.OrderRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderAdminServiceTest {
    @InjectMocks
    private OrderAdminService orderAdminService;

    @Mock
    private OrderRepository orderRepository;

    @Test
    void shouldDeleteAllOrders() {
        //When
        //Given
        orderAdminService.deleteAllOrders();


        //Then
        verify(orderRepository, times(1)).deleteAll();
    }

    @Test
    void shouldReturnOrderRepository() {
        //Given
        //When
        OrderRepository result = orderAdminService.orderRepository();

        //Then
        assertEquals(orderRepository, result);
    }
}