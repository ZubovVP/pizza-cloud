package ru.zubov.pizzacloud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
//@RestResource(rel="pizzas", path="pizza")       // позволяет поменять endpoint для запроса data rest
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 4, message = "Name must be at least 4 characters long")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ingredient_pizza",
            joinColumns = { @JoinColumn(name = "pizza_id") },
            inverseJoinColumns = { @JoinColumn(name = "ingredients_id") })
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name="pizza_order_id")
    private PizzaOrder pizzaOrder;

}
