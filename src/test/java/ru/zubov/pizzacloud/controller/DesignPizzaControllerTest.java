package ru.zubov.pizzacloud.controller;

import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.zubov.pizzacloud.entity.Ingredient;
import ru.zubov.pizzacloud.entity.dtos.IngredientDto;
import ru.zubov.pizzacloud.entity.dtos.PizzaDto;
import ru.zubov.pizzacloud.entity.dtos.PizzaOrderDto;
import ru.zubov.pizzacloud.entity.dtos.UserDto;
import ru.zubov.pizzacloud.entity.mapper.PizzaMapperImpl;
import ru.zubov.pizzacloud.entity.mapper.PizzaOrderMapperImpl;
import ru.zubov.pizzacloud.repository.IngredientRepository;
import ru.zubov.pizzacloud.repository.PizzaRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DesignPizzaController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("development")
class DesignPizzaControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private PizzaRepository pizzaRepository;

    @MockBean
    private PizzaMapperImpl pizzaMapper;

    @MockBean
    private PizzaOrderMapperImpl pizzaOrderMapper;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void checkThenContextHaveDesignPizzaController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("designPizzaController"));
    }

    @Test
    public void showDesignForm() throws Exception {
        mockMvc.perform(get("/design"))
                .andExpect(status().isOk())
                .andExpect(view().name("design"))
                .andExpect(content().string(
                        containsString("Design your pizza!")));
    }

    @Test
    public void processPizza() throws Exception {

        PizzaDto pizza = new PizzaDto(222L, "Pizza_name", List.of(new IngredientDto("0", "someIngr",
                Ingredient.Type.PROTEIN)), null, null);

        UserDto userDto = new UserDto(1L, "user", "fullName", null, null, null, null, null);

        PizzaOrderDto pizzaOrder = new PizzaOrderDto(111L, null, null, null,
                null, null, null, null, null, LocalDateTime.now(), List.of(pizza), userDto);

        when(pizzaMapper.pizzaDtoToPizza(any())).thenCallRealMethod();
        when(pizzaOrderMapper.pizzaOrderDtoToPizzaOrder(any())).thenCallRealMethod();

        mockMvc.perform(post("/design")
                        .flashAttr("pizzaOrderDto", pizzaOrder)
                        .flashAttr("pizzaDto", pizza)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/orders/current"));
    }
}