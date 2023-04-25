package ru.zubov.pizzacloud.repository;

import org.springframework.data.repository.CrudRepository;
import ru.zubov.pizzacloud.entity.PizzaOrder;

public interface OrderRepository extends CrudRepository<PizzaOrder, Long> {
    PizzaOrder save(PizzaOrder order);
}
