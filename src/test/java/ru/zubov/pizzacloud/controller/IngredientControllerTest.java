package ru.zubov.pizzacloud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.zubov.pizzacloud.controller.rest.IngredientController;
import ru.zubov.pizzacloud.entity.Ingredient;
import ru.zubov.pizzacloud.entity.RoleUser;
import ru.zubov.pizzacloud.entity.SignUpDto;
import ru.zubov.pizzacloud.entity.User;
import ru.zubov.pizzacloud.entity.dtos.IngredientDto;
import ru.zubov.pizzacloud.entity.mapper.IngredientMapperImpl;
import ru.zubov.pizzacloud.repository.IngredientRepository;
import ru.zubov.pizzacloud.service.CustomUserDetailsService;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @MockBean
    private IngredientMapperImpl ingredientMapper;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void checkThenContextHaveDesignPizzaController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("ingredientController"));
    }

    @Test
    public void getAllEmployeesAPI() throws Exception {
        Ingredient ingredient = new Ingredient("id", "name", Ingredient.Type.PROTEIN);

        when(ingredientRepository.findAll()).thenReturn(List.of(ingredient));
        doCallRealMethod().when(ingredientMapper).ingredientToIngredientDto(ingredient);

        mvc.perform(get("/api/ingredients"))
                .andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo("id")))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("name")))
                .andExpect(jsonPath("$[0].type").value("PROTEIN"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postCreateIngredient() throws Exception {
        IngredientDto ingredient = new IngredientDto("id", "name", Ingredient.Type.PROTEIN);

        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setLogin("abc");
        signUpDto.setPassword("123");
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        User user = new User();
        user.getAuthorities().add(new RoleUser(1L, "ROLE_ADMIN", null));
        when(customUserDetailsService.loadUserByUsername(any())).thenReturn(user);

        doCallRealMethod().when(ingredientMapper).ingredientDtoToIngredient(ingredient);

        mvc.perform(post("/api/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(ingredient))
                        .content(asJsonString(Map.of("signUpDto", signUpDto, "ingredient", ingredient))))
                .andExpect(status().isCreated());

        verify(ingredientRepository, times(1)).save(argThat(i -> i.getType() == Ingredient.Type.PROTEIN));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteDeleteIngredient() throws Exception {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setLogin("abc");
        signUpDto.setPassword("123");
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        User user = new User();
        user.getAuthorities().add(new RoleUser(1L, "ROLE_ADMIN", null));
        when(customUserDetailsService.loadUserByUsername(any())).thenReturn(user);
        mvc.perform(delete("/api/ingredients/{id}", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signUpDto)))
                .andExpect(status().isOk());
        verify(ingredientRepository, times(1)).deleteById("123");
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}