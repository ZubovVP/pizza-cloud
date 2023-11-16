package ru.zubov.pizzacloud.message.rabbitMQ;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import ru.zubov.pizzacloud.entity.PizzaOrder;

@Component
@RequiredArgsConstructor
public class RabbitOrderReceiver {
    private final RabbitTemplate rabbit;

    /**
     * Активный приём сообщений (нужно постоянно вызывать этот метод)
     * @return PizzaOrder
     */
    public PizzaOrder receiveOrder() {
        return rabbit.receiveAndConvert("pizzacloud.order.queue",
                new ParameterizedTypeReference<>() {
                });
    }

    /**
     * Пассивный приём сообщений (НЕ нужно постоянно вызывать этот метод)
     *
     */
    @RabbitListener(queues = "tacocloud.order.queue")
    public void receiveOrder(PizzaOrder order) {

        //Выполнение какого-либо действия
        //        ui.displayOrder(order);
    }
}
