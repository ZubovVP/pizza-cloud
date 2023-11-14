package ru.zubov.pizzacloud.message;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import ru.zubov.pizzacloud.entity.PizzaOrder;

@Component
@RequiredArgsConstructor
public class RabbitOrderReceiver {
    private final RabbitTemplate rabbit;
    private final MessageConverter converter;

    public PizzaOrder receiveOrder() {
        Message message = rabbit.receive("pizzacloud.order.queue");
        return message != null
                ? (PizzaOrder) converter.fromMessage(message)
                : null;
    }
}
