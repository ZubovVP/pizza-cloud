package ru.zubov.pizzacloud.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PizzaOrder {
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCW;

    private List<Pizza> pizza = new ArrayList<>();

    public void addPizza(Pizza pizza) {
        this.pizza.add(pizza);
    }
}
