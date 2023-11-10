package ru.zubov.pizzacloud.message;

import jakarta.jms.Destination;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import ru.zubov.pizzacloud.entity.PizzaOrder;

@Service
@RequiredArgsConstructor
public class JmsOrderMessagingService implements OrderMessagingService {
    private final JmsTemplate jms;
    private final Destination orderQueue;

    @Override
    public void sendOrder(PizzaOrder order) {
        jms.send(orderQueue,
                session -> session.createObjectMessage(order)
        );
    }
}
