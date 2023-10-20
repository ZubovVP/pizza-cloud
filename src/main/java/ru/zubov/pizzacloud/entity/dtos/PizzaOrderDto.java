package ru.zubov.pizzacloud.entity.dtos;

import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serializable;
import java.time.LocalDateTime;

public record PizzaOrderDto(long id, String deliveryName, String deliveryStreet, String deliveryCity,
                            String deliveryState, String deliveryZip,
                            @CreditCardNumber(message = "Not a valid credit card number") String ccNumber,
                            String ccExpiration, String ccCVV, LocalDateTime placedAt) implements Serializable {
}
