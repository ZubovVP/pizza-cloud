package ru.zubov.pizzacloud.entity;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;
    public User toUser(PasswordEncoder passwordEncoder, List<RoleUser> roleUsers) {
        User user = new User(
                username, passwordEncoder.encode(password),
                fullname, street, city, state, zip, phone);
        user.getAuthorities().addAll(roleUsers);
        return user;
    }
}
