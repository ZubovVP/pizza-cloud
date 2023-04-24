package ru.zubov.pizzacloud.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Pizza {
    private long id;

    @NotNull
    @Size(min = 4, message = "Name must be at least 4 characters long")
    private String name;

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<IngredientRef> ingredients;

    private LocalDateTime createdAt = LocalDateTime.now();

}
