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
        produces={"application/json", "text/xml"})      //клиент может обрабатывать ответы тольков формате JSON или xml
@CrossOrigin(origins={"http://pizzacloud:8080", "http://pizzacloud.com"})   //Это обход ограничение, включается заголовки CORS (Cross-Origin Resource Sharing – совместное использование ресурсов между источниками)
@RequiredArgsConstructor
public class PizzaController {
    private final PizzaRepository repository;

    @GetMapping(params="recent")
    public Iterable<Pizza> recentPizza() {
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createdAt").descending());
        return repository.findAll(page).getContent();
    }
}
