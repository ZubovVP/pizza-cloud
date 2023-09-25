package ru.zubov.pizzacloud.controller;

import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.zubov.pizzacloud.config.OrderProps;
import ru.zubov.pizzacloud.entity.Ingredient;
import ru.zubov.pizzacloud.entity.Pizza;
import ru.zubov.pizzacloud.entity.PizzaOrder;
import ru.zubov.pizzacloud.entity.User;
import ru.zubov.pizzacloud.repository.IngredientRepository;
import ru.zubov.pizzacloud.repository.OrderRepository;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private OrderProps orderProps;

    @MockBean
    private IngredientRepository ingredientRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void checkThenContextHaveDesignPizzaController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("orderController"));
    }


    @Test
    public void testOrdersPage() throws Exception {
        Mockito.when(this.orderProps.getPageSize()).thenReturn(20);

        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());

        PizzaOrder pizzaOrder = new PizzaOrder();
        User user = mock(User.class);
        when(user.getFullname()).thenReturn("abc");
        pizzaOrder.setUser(user);

        Mockito.when(this.orderRepository.findByUserOrderByPlacedAtDesc(null, pageable)).thenReturn(List.of(pizzaOrder));


        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderList"))
                .andExpect(content().string(
                        containsString("Your orders:")));
    }

    @Test
    public void testCurrentPage() throws Exception {
        PizzaOrder pizzaOrder = new PizzaOrder();
        mockMvc.perform(get("/orders/current")
                        .flashAttr("pizzaOrder", pizzaOrder))
                .andExpect(status().isOk())
                .andExpect(view().name("orderForm"))
                .andExpect(content().string(
                        containsString("Order your pizza creations!")));
    }

    @Test
    public void processOrder() throws Exception {
        PizzaOrder pizzaOrder = mock(PizzaOrder.class);
        pizzaOrder.setId(111L);

        Pizza pizza = new Pizza();
        pizza.setName("Pizza_name");
        pizza.setId(222L);
        pizza.setIngredients(List.of(new Ingredient()));
        mockMvc.perform(post("/orders")
                        .flashAttr("pizzaOrder", pizzaOrder)
                        .flashAttr("pizza", pizza)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        verify(pizzaOrder, times(1)).setPlacedAt(any());
        verify(pizzaOrder, times(1)).setUser(any());
        verify(orderRepository, times(1)).save(pizzaOrder);
    }
}