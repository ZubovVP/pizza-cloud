package ru.zubov.pizzacloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zubov.pizzacloud.entity.RoleUser;

@Repository
public interface RoleRepository extends JpaRepository<RoleUser, Long> {
}
