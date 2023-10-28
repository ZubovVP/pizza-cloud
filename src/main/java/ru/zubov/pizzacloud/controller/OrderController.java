package ru.zubov.pizzacloud.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.zubov.pizzacloud.config.OrderProps;
import ru.zubov.pizzacloud.entity.PizzaOrder;
import ru.zubov.pizzacloud.entity.User;
import ru.zubov.pizzacloud.entity.dtos.PizzaOrderDto;
import ru.zubov.pizzacloud.entity.mapper.PizzaOrderMapper;
import ru.zubov.pizzacloud.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("pizzaOrder")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderProps orderProps;
    private final PizzaOrderMapper pizzaOrderMapper;

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid PizzaOrderDto dto, Errors errors,
                               SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if (errors.hasErrors() && user != null) {
            return "orderForm";
        }
        PizzaOrder pizzaOrder = pizzaOrderMapper.pizzaOrderDtoToPizzaOrder(dto);


        pizzaOrder.setPlacedAt(LocalDateTime.now());
        pizzaOrder.setUser(user);

        log.info("Order submitted: {}", dto);
        orderRepository.save(pizzaOrder);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(@RequestParam(defaultValue = "0") Integer page, @AuthenticationPrincipal User user, Model model) {
        Pageable pageable = PageRequest.of(page, orderProps.getPageSize());

        List<PizzaOrder> byUserOrderByPlacedAtDesc = orderRepository.findByUserOrderByPlacedAtDesc(user, pageable);
        model.addAttribute("orders", byUserOrderByPlacedAtDesc.stream()
                .map(pizzaOrderMapper::pizzaOrderToPizzaOrderDto)
                .collect(Collectors.toList()));
        return "orderList";
    }
}
