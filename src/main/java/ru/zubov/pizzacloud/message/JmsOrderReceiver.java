package ru.zubov.pizzacloud.message;

import jakarta.jms.Destination;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import ru.zubov.pizzacloud.entity.PizzaOrder;

@Component
@RequiredArgsConstructor
public class JmsOrderReceiver {
    private final JmsTemplate jms;
    private final Destination destination;

    //Активная модель pull - при вызове метода, поток будет дожидаться, пока заказ не поступит в очередь
    //Наиболее подходящая модель, когда заказы должны поступать в своём темпе
    public PizzaOrder receiveOrder() {
        return (PizzaOrder) jms.receiveAndConvert(destination);
    }


    /*
    Пример, когда метод будет автоматически идёт вызов метода, когда в данную очередь поступит заказ.
    Не во всех случаях применим. Применим, когда необходимо быстро обработать сообщения.
     */
//    @JmsListener(destination = "pizzacloud.order.queue")
//    public void receiveOrder(PizzaOrder order) {
//        ui.displayOrder(order);
//    }
}