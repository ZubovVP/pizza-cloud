package ru.zubov.pizzacloud.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.zubov.pizzacloud.IngredientByIdConverter;
import ru.zubov.pizzacloud.repository.OrderRepository;
import ru.zubov.pizzacloud.repository.PizzaRepository;

@WebMvcTest(PizzaController.class)
@WithMockUser(username="admin",roles="ADMIN")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class PizzaControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PizzaRepository repository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private IngredientByIdConverter ingredientByIdConverter;

    @Test
    void testDelete() throws Exception {
//        mockMvc.perform(get("/api/pizza/1")
//                .header("Access-Control-Request-Method", "GET")
//                .header("Origin", "http://pizzacloud.com"))
//                .andExpect(status().isOk());

//        mockMvc.perform(get("/api/pizza/1").with(csfr()))
//                .andExpect(status().isOk());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/pizza/1").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result);


//        mockMvc.perform(delete("/api/pizza/{orderId}", "1"))
//                .andExpect(status().isNoContent())
//                .andDo(print());
    }
}