package ru.zubov.pizzacloud.message.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.zubov.pizzacloud.entity.PizzaOrder;
import ru.zubov.pizzacloud.message.OrderMessagingService;

@Service
@RequiredArgsConstructor
public class KafkaOrderMessagingService implements OrderMessagingService {
    private final KafkaTemplate<String, PizzaOrder> kafkaTemplate;

    @Override
    public void sendOrder(PizzaOrder order) {
//        kafkaTemplate.send("pizzacloud.orders.topic", order);
//        or (указано в yaml)
        kafkaTemplate.sendDefault(order);
    }
}
