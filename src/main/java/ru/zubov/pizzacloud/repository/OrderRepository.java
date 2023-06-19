package ru.zubov.pizzacloud.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.zubov.pizzacloud.entity.PizzaOrder;
import ru.zubov.pizzacloud.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<PizzaOrder, Long> {

    List<PizzaOrder> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, LocalDateTime startDate, LocalDateTime endDate);

    List<PizzaOrder> findByDeliveryNameAndDeliveryCity(String deliveryName, String deliveryCity);

    @Query("SELECT o FROM PizzaOrder o where o.deliveryCity='Seattle'")
    List<PizzaOrder> readOrdersDeliveredInSeattle();

    List<PizzaOrder> findByUserOrderByPlacedAtDesc(User use, Pageable pageable);
}
