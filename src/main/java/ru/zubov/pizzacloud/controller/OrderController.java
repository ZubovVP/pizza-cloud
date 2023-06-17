package ru.zubov.pizzacloud.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import ru.zubov.pizzacloud.entity.PizzaOrder;
import ru.zubov.pizzacloud.entity.User;
import ru.zubov.pizzacloud.repository.IngredientRepository;
import ru.zubov.pizzacloud.repository.OrderRepository;
import ru.zubov.pizzacloud.repository.PizzaRepository;
import ru.zubov.pizzacloud.repository.UserRepository;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("pizzaOrder")
public class OrderController {
    private final OrderRepository orderRepository;
    private final PizzaRepository pizzaRepository;
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;

    public OrderController(OrderRepository orderRepository, PizzaRepository pizzaRepository, IngredientRepository ingredientRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.pizzaRepository = pizzaRepository;
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid PizzaOrder order, Errors errors,
                               SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if (errors.hasErrors() && user != null) {
            return "orderForm";
        }

        pizzaRepository.saveAll(order.getPizza());
        order.setPlacedAt(LocalDateTime.now());
        order.setUser(user);

        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
