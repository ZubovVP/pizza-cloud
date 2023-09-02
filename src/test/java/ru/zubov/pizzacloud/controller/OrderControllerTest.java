package ru.zubov.pizzacloud.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.zubov.pizzacloud.config.OrderProps;
import ru.zubov.pizzacloud.entity.PizzaOrder;
import ru.zubov.pizzacloud.repository.OrderRepository;
import ru.zubov.pizzacloud.repository.PizzaRepository;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private PizzaRepository pizzaRepository;

    @MockBean
    private OrderProps orderProps;


    @Test
    public void testHomePage() throws Exception {
        Mockito.when(this.orderProps.getPageSize()).thenReturn(20);

        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());

        PizzaOrder pizzaOrder = new PizzaOrder();

        Mockito.when(this.orderRepository.findByUserOrderByPlacedAtDesc(null, pageable)).thenReturn(List.of(pizzaOrder));


        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderList"))
                .andExpect(content().string(
                        containsString("Your orders")));
    }


}