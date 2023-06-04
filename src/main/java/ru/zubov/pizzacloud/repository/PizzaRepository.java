package ru.zubov.pizzacloud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.zubov.pizzacloud.entity.Pizza;

@Repository
public interface PizzaRepository extends CrudRepository<Pizza, Long> {
}
