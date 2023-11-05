package ru.zubov.pizzacloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable().authorizeHttpRequests()
                .requestMatchers("/design", "/orders").access(AuthorityAuthorizationManager.hasAnyRole("ADMIN", "USER"))
                //включить после настройки каскадного удаления пицц при удаление ингредиентов
//                .requestMatchers(HttpMethod.POST, "/api/ingredients").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/api/ingredients/**").hasRole("ADMIN")
                .requestMatchers("/", "/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/design", true)
                .loginPage("/login").permitAll()
                .and()
                .build();
    }

//    @Bean
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(
//                        authorizeRequests -> authorizeRequests.anyRequest().authenticated()
//                )
//                .oauth2Login(
//                        oauth2Login ->
//                                oauth2Login.loginPage("/oauth2/authorization/taco-admin-client"))
//                .oauth2Client(withDefaults());
//        return http.build();
//    }
}