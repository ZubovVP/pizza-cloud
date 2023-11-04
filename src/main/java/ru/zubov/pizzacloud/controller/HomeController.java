package ru.zubov.pizzacloud.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.zubov.pizzacloud.entity.User;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal User user) {
        return "home";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}
