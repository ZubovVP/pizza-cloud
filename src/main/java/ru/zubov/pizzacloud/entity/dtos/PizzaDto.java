package ru.zubov.pizzacloud.entity.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PizzaDto implements Serializable {
    private final long id;
    private final String name;
    private final List<IngredientDto> ingredients;
    private final LocalDateTime createdAt;
    private final PizzaOrderDto pizzaOrder;
}
