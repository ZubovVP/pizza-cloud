package ru.zubov.pizzacloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PizzaCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzaCloudApplication.class, args);
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        List<UserDetails> usersList = new ArrayList<>();
//        usersList.add(new User(
//                "buzz", encoder.encode("password"),
//                List.of(new SimpleGrantedAuthority("ROLE_USER"))));
//        usersList.add(new User(
//                "woody", encoder.encode("password"),
//                List.of(new SimpleGrantedAuthority("ROLE_USER"))));
//        return new InMemoryUserDetailsManager(usersList);
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
