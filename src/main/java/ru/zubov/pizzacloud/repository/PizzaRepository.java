package ru.zubov.pizzacloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zubov.pizzacloud.entity.Pizza;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {
}
