package ru.zubov.pizzacloud.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.zubov.pizzacloud.entity.Ingredient;
import ru.zubov.pizzacloud.entity.dtos.IngredientDto;
import ru.zubov.pizzacloud.entity.mapper.IngredientMapper;
import ru.zubov.pizzacloud.repository.IngredientRepository;

import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/ingredients", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientRepository repo;
    private final IngredientMapper ingredientMapper;

    @GetMapping
    public Iterable<IngredientDto> allIngredients() {
        return repo.findAll().stream()
                .map(ingredientMapper::ingredientToIngredientDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    public Ingredient saveIngredient(@RequestBody IngredientDto ingredient) {
        return repo.save(ingredientMapper.ingredientDtoToIngredient(ingredient));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable("id") String ingredientId) {
        repo.deleteById(ingredientId);
    }
}
