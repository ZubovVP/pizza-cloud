package ru.zubov.pizzacloud.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.zubov.pizzacloud.IngredientByIdConverter;
import ru.zubov.pizzacloud.entity.Pizza;
import ru.zubov.pizzacloud.repository.OrderRepository;
import ru.zubov.pizzacloud.repository.PizzaRepository;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PizzaController.class)
@WithMockUser(username = "ADMIN", roles = "ADMIN")
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
    void testPizzaById() throws Exception {
        when(repository.findById(any())).thenReturn(Optional.of(new Pizza()));

        mockMvc.perform(get("/api/pizza/1")
                        .header("Access-Control-Request-Method", "GET")
                        .header("Origin", "http://pizzacloud.com"))
                .andExpect(status().isOk());
    }

    @Test
    void testRecentPizza() throws Exception {
        Pizza p = new Pizza();
        p.setId(123L);

        Page pageable = mock(Page.class);
        when(pageable.getContent()).thenReturn(List.of(p));

        when(repository.findAll(any(PageRequest.class))).thenReturn(pageable);

        mockMvc.perform(get("/api/pizza?recent=123")
                        .header("Access-Control-Request-Method", "GET")
                        .header("Origin", "http://pizzacloud.com"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo(123))
        );
    }

    @Test
    void testFindById() throws Exception {
        when(repository.findById(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/pizza/123")
                        .header("Access-Control-Request-Method", "GET")
                        .header("Origin", "http://pizzacloud.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreatePizza() throws Exception {
        JSONObject pizzaJson = new JSONObject();
        pizzaJson.put("id", "123");

        MvcResult result = mockMvc.perform(post("/api/pizza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pizzaJson.toString())
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andReturn();
    }
}