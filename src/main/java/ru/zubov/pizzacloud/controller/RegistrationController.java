package ru.zubov.pizzacloud.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zubov.pizzacloud.entity.RegistrationForm;
import ru.zubov.pizzacloud.entity.User;
import ru.zubov.pizzacloud.repository.RoleRepository;
import ru.zubov.pizzacloud.repository.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
        User user = userRepository.save(form.toUser(passwordEncoder));
        user.getAuthorities().add(roleRepository.findById(1L).orElseThrow());
        userRepository.save(user);
        return "redirect:/login";
    }
}
