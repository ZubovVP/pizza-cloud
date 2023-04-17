package ru.zubov.pizzacloud.entity;

import lombok.Data;

@Data
public class Ingredient {
    private final String id;
    private final String name;
    private final Type type;

    public enum Type {
        SIZE, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
