package ru.zubov.pizzacloud.entity.dtos;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PizzaOrderDto implements Serializable {
    private final long id;
    private final String deliveryName;
    private final String deliveryStreet;
    private final String deliveryCity;
    private final String deliveryState;
    private final String deliveryZip;
    @CreditCardNumber(message = "Not a valid credit card number")
    private final String ccNumber;
    private final String ccExpiration;
    private final String ccCVV;
    private final LocalDateTime placedAt;
}
