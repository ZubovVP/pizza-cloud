package ru.zubov.pizzacloud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @ManyToMany
    @JoinTable(name = "ingredient_pizza",
            joinColumns = { @JoinColumn(name = "ingredients_id") },
            inverseJoinColumns = { @JoinColumn(name = "pizza_id") })
    private List<Pizza> pizza = new ArrayList<>();

    public enum Type {
        SIZE, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
