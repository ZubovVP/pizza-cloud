package ru.zubov.pizzacloud.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zubov.pizzacloud.entity.Pizza;
import ru.zubov.pizzacloud.entity.PizzaOrder;
import ru.zubov.pizzacloud.repository.OrderRepository;
import ru.zubov.pizzacloud.repository.PizzaRepository;

import java.util.Optional;

//@RestController
@RequestMapping(path = "/api/pizza",
        produces = {"application/json", "text/xml"})
//клиент может обрабатывать ответы тольков формате JSON или xml
@CrossOrigin(origins = {"http://pizzacloud:8080", "http://pizzacloud.com"})
//Это обход ограничение, включается заголовки CORS (Cross-Origin Resource Sharing – совместное использование ресурсов между источниками)
@RequiredArgsConstructor
public class PizzaController {
    private final PizzaRepository repository;
    private final OrderRepository orderRepository;

    @GetMapping(params = "recent")
    public Iterable<Pizza> recentPizza() {
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createdAt").descending());
        return repository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pizza> pizzaById(@PathVariable("id") Long id) {
        Optional<Pizza> optPizza = repository.findById(id);
        return optPizza.map(pizza -> new ResponseEntity<>(pizza, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Pizza postPizza(@RequestBody Pizza pizza) {
        return repository.save(pizza);
    }

    @PutMapping(path="/{orderId}", consumes="application/json")
    public PizzaOrder putOrder(
            @PathVariable("orderId") Long orderId,
            @RequestBody PizzaOrder order) {
        order.setId(orderId);
        return orderRepository.save(order);
    }

    @PatchMapping(path="/{orderId}", consumes="application/json")
    public PizzaOrder patchOrder(@PathVariable("orderId") Long orderId,
                                @RequestBody PizzaOrder patch) {
        PizzaOrder order = orderRepository.findById(orderId).get();
        if (patch.getDeliveryName() != null) {
            order.setDeliveryName(patch.getDeliveryName());
        }
        if (patch.getDeliveryStreet() != null) {
            order.setDeliveryStreet(patch.getDeliveryStreet());
        }
        if (patch.getDeliveryCity() != null) {
            order.setDeliveryCity(patch.getDeliveryCity());
        }
        if (patch.getDeliveryState() != null) {
            order.setDeliveryState(patch.getDeliveryState());
        }
        if (patch.getDeliveryZip() != null) {
            order.setDeliveryZip(patch.getDeliveryZip());
        }
        if (patch.getCcNumber() != null) {
            order.setCcNumber(patch.getCcNumber());
        }
        if (patch.getCcExpiration() != null) {
            order.setCcExpiration(patch.getCcExpiration());
        }
        if (patch.getCcCVV() != null) {
            order.setCcCVV(patch.getCcCVV());
        }
        return orderRepository.save(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException();
        }
    }
}
