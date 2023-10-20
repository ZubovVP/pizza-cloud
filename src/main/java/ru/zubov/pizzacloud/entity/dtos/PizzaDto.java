package ru.zubov.pizzacloud.entity.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record PizzaDto(long id, String name, List<IngredientDto> ingredients, LocalDateTime createdAt,
                       PizzaOrderDto pizzaOrder) implements Serializable {
}
