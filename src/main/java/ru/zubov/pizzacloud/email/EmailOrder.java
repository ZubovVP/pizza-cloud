package ru.zubov.pizzacloud.email;

import lombok.Data;
import ru.zubov.pizzacloud.entity.Pizza;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmailOrder {
    private final String email;
    private List<Pizza> pizza = new ArrayList<>();
    public void addTaco(Pizza pizza) {
        this.pizza.add(pizza);
    }
}
