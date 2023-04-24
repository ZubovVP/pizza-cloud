package ru.zubov.pizzacloud.repository;

import ru.zubov.pizzacloud.entity.PizzaOrder;

public interface OrderRepository {
    PizzaOrder save(PizzaOrder order);
}
