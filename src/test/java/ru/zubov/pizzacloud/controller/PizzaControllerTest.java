package ru.zubov.pizzacloud.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.zubov.pizzacloud.IngredientByIdConverter;
import ru.zubov.pizzacloud.repository.OrderRepository;
import ru.zubov.pizzacloud.repository.PizzaRepository;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PizzaController.class)
//@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("development")
class PizzaControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PizzaRepository repository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private IngredientByIdConverter ingredientByIdConverter;

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/pizza/{orderId}", "1"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}