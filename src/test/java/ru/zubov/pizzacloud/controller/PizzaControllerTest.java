package ru.zubov.pizzacloud.controller;

import jakarta.servlet.ServletContext;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;
import ru.zubov.pizzacloud.IngredientByIdConverter;
import ru.zubov.pizzacloud.entity.Pizza;
import ru.zubov.pizzacloud.entity.PizzaOrder;
import ru.zubov.pizzacloud.entity.mapper.PizzaMapperImpl;
import ru.zubov.pizzacloud.entity.mapper.PizzaOrderMapperImpl;
import ru.zubov.pizzacloud.repository.OrderRepository;
import ru.zubov.pizzacloud.repository.PizzaRepository;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private IngredientByIdConverter ingredientByIdConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private PizzaMapperImpl pizzaMapper;

    @MockBean
    private PizzaOrderMapperImpl pizzaOrderMapper;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void checkThenContextHaveRegistrationController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("pizzaController"));
    }

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
        doCallRealMethod().when(pizzaMapper).pizzaToPizzaDto(p);

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

        doCallRealMethod().when(pizzaMapper).pizzaDtoToPizza(any());

        mockMvc.perform(post("/api/pizza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pizzaJson.toString())
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated());

        verify(repository, times(1)).save(argThat(pizza -> pizza.getId() == 123L));
    }

    @Test
    void testSaveOrder() throws Exception {
        JSONObject pizzaJson = new JSONObject();
        pizzaJson.put("id", "111");

        mockMvc.perform(put("/api/pizza/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pizzaJson.toString())
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());

        verify(orderRepository, times(1)).save(argThat(order -> order.getId() == 123L));
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public PizzaOrder patchOrder(@PathVariable("orderId") Long orderId,
                                 @RequestBody PizzaOrder patch) {
        PizzaOrder order = orderRepository.findById(orderId).get();
        if (patch.getDeliveryName() != null) {
            order.setDeliveryName(patch.getDeliveryName());
        }
        if (patch.getDeliveryStreet() != null) {
            order.setDeliveryStreet(patch.getDeliveryStreet());
        }
        if (patch.getDeliveryCity() != null) {
            order.setDeliveryCity(patch.getDeliveryCity());
        }
        if (patch.getDeliveryState() != null) {
            order.setDeliveryState(patch.getDeliveryState());
        }
        if (patch.getDeliveryZip() != null) {
            order.setDeliveryZip(patch.getDeliveryZip());
        }
        if (patch.getCcNumber() != null) {
            order.setCcNumber(patch.getCcNumber());
        }
        if (patch.getCcExpiration() != null) {
            order.setCcExpiration(patch.getCcExpiration());
        }
        if (patch.getCcCVV() != null) {
            order.setCcCVV(patch.getCcCVV());
        }
        return orderRepository.save(order);
    }

    @Test
    void testPatchOrder() throws Exception {
        JSONObject pizzaOrder = new JSONObject();
        pizzaOrder.put("id", "111");
        pizzaOrder.put("deliveryName", "name");
        pizzaOrder.put("deliveryStreet", "street");
        pizzaOrder.put("deliveryCity", "city");
        pizzaOrder.put("deliveryState", "state");
        pizzaOrder.put("deliveryZip", "zip");
        pizzaOrder.put("ccNumber", "123456789");
        pizzaOrder.put("ccExpiration", "11/29");
        pizzaOrder.put("ccCVV", "789");
        pizzaOrder.put("ccCVV", "789");

        when(orderRepository.findById(123L)).thenReturn(Optional.of(new PizzaOrder()));

        mockMvc.perform(patch("/api/pizza/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pizzaOrder.toString())
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());

        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void testDeleteOrderPizza() throws Exception {
        mockMvc.perform(delete("/api/pizza/{orderId}", 123)
                        .characterEncoding("utf-8"))
                .andExpect(status().isNoContent());

        verify(orderRepository, times(1)).deleteById(123L);
    }
}