package ru.zubov.pizzacloud.message.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import ru.zubov.pizzacloud.entity.PizzaOrder;

@Component
@Slf4j
public class OrderListener {

    @KafkaListener(topics="pizzacloud.orders.topic")
    public void handle(PizzaOrder order, Message<PizzaOrder> message) {
        MessageHeaders headers = message.getHeaders();
        log.info("Received from partition {} with timestamp {}",
                headers.get(KafkaHeaders.RECEIVED_PARTITION),
                headers.get(KafkaHeaders.RECEIVED_TIMESTAMP));

        //Выполнение какого-либо действия
        //        ui.displayOrder(order);
    }
}
