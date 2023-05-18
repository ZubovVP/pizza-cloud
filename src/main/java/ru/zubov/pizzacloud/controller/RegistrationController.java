package ru.zubov.pizzacloud.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zubov.pizzacloud.repository.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    @GetMapping
//    public String registerForm() {
//        return "registration";
//    }
//
//    @PostMapping
//    public String processRegistration(RegistrationForm form) {
//        userRepository.save(form.toUser(passwordEncoder));
//        return "redirect:/login";
//    }
}
