package ru.zubov.pizzacloud.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zubov.pizzacloud.entity.Pizza;
import ru.zubov.pizzacloud.repository.PizzaRepository;

@RestController
@RequestMapping(path="/api/pizza",
        produces="application/json")
@CrossOrigin(origins="http://pizzacloud:8080")
@RequiredArgsConstructor
public class PizzaController {
    private final PizzaRepository repository;

    @GetMapping(params="recent")
    public Iterable<Pizza> recentTacos() {
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createdAt").descending());
        return repository.findAll(page).getContent();
    }
}
