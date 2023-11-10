package ru.zubov.pizzacloud.controller.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.zubov.pizzacloud.entity.PizzaOrder;
import ru.zubov.pizzacloud.message.OrderMessagingService;
import ru.zubov.pizzacloud.repository.OrderRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api/orders",
        produces="application/json")
@CrossOrigin(origins="http://localhost:8080")
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderMessagingService messagingService;

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public PizzaOrder postOrder(@RequestBody PizzaOrder order) {
        messagingService.sendOrder(order);
        return orderRepository.save(order);
    }

}
