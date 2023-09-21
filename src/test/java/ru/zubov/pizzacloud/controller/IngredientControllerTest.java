package ru.zubov.pizzacloud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.zubov.pizzacloud.entity.Ingredient;
import ru.zubov.pizzacloud.repository.IngredientRepository;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IngredientController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("development")
class IngredientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IngredientRepository ingredientRepository;

    @Test
    public void getAllEmployeesAPI() throws Exception
    {
        Ingredient ingredient = new Ingredient("id", "name", Ingredient.Type.PROTEIN);

        when(ingredientRepository.findAll()).thenReturn(List.of(ingredient));

        mvc.perform(get("/api/ingredients")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo("id")))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("name")))
                .andExpect(jsonPath("$[0].type").value("PROTEIN"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}