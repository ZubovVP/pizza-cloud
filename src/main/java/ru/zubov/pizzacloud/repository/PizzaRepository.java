package ru.zubov.pizzacloud.repository;

import org.springframework.data.repository.CrudRepository;
import ru.zubov.pizzacloud.entity.Pizza;

public interface PizzaRepository extends CrudRepository<Pizza, Long> {
}
