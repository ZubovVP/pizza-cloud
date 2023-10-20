package ru.zubov.pizzacloud.entity.dtos;

import ru.zubov.pizzacloud.entity.Ingredient;

import java.io.Serializable;

public record IngredientDto(String id, String name, Ingredient.Type type) implements Serializable {
}
