package ru.zubov.pizzacloud.message;

import ru.zubov.pizzacloud.entity.PizzaOrder;

public interface OrderMessagingService {

    void sendOrder(PizzaOrder order);
}
