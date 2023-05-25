package ru.zubov.pizzacloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.zubov.pizzacloud.entity.RoleUser;
import ru.zubov.pizzacloud.entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        List<UserDetails> usersList = new ArrayList<>();
        RoleUser roleUser = new RoleUser(2L, "ROLE_USER", new HashSet<>());
        usersList.add(new User(
                "buzz", encoder.encode("password"),
                List.of(roleUser)));
        usersList.add(new User(
                "woody", encoder.encode("password"),
                List.of(roleUser)));
        return new InMemoryUserDetailsManager(usersList);
    }
}