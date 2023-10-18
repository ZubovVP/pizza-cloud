package ru.zubov.pizzacloud.entity.dtos;

import lombok.Data;
import ru.zubov.pizzacloud.entity.Ingredient;

import java.io.Serializable;

@Data
public class IngredientDto implements Serializable {
    private final String id;
    private final String name;
    private final Ingredient.Type type;
}
