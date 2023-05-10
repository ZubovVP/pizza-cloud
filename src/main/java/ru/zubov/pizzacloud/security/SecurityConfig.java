package ru.zubov.pizzacloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(UserRepository userRepo) {
//        return username -> {
//            User user = userRepo.findByUsername(username);
//            if (user != null){
//                return user;
//            }
//            throw new UsernameNotFoundException("User " + username + " not found");
//        };
//    }
//
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
                .requestMatchers("/design", "/orders").access(AuthorityAuthorizationManager.hasRole("USER"))
                .requestMatchers("/", "/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/design", true)
//                .loginProcessingUrl("/authenticate")
//                .usernameParameter("user")
//                .passwordParameter("pwd")
                .and()
                .build();
    }
}
