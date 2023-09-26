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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.zubov.pizzacloud.repository.IngredientRepository;
import ru.zubov.pizzacloud.repository.RoleRepository;
import ru.zubov.pizzacloud.repository.UserRepository;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class RegistrationControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepository repository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void checkThenContextHaveRegistrationController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("registrationController"));
    }

    @Test
    public void checkRegisterForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(content().string(
                        containsString("Register")));
    }
}