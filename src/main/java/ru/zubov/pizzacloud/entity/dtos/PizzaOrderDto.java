package ru.zubov.pizzacloud.entity.dtos;

import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record PizzaOrderDto(long id, String deliveryName, String deliveryStreet, String deliveryCity,
                            String deliveryState, String deliveryZip,
                            @CreditCardNumber(message = "Not a valid credit card number") String ccNumber,
                            String ccExpiration, String ccCVV, LocalDateTime placedAt, List<PizzaDto> pizza,
                            UserDto user) implements Serializable {
}
