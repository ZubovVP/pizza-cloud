package ru.zubov.pizzacloud.message.rabbitMQ;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.zubov.pizzacloud.entity.PizzaOrder;
import ru.zubov.pizzacloud.message.OrderMessagingService;

@RequiredArgsConstructor
public class RabbitOrderMessagingService implements OrderMessagingService {
    private final RabbitTemplate rabbit;

    @Override
    public void sendOrder(PizzaOrder order) {
        rabbit.convertAndSend("pizzacloud.order.queue", order,
                message -> {
                    MessageProperties props = message.getMessageProperties();
                    props.setHeader("X_ORDER_SOURCE", "WEB");
                    return message;
                });
    }
}
