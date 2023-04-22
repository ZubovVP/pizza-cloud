package ru.zubov.pizzacloud.repository;

import ru.zubov.pizzacloud.entity.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository {
    List<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);
}
