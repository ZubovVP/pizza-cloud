package ru.zubov.pizzacloud.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "pizza.orders")
@Data
@Validated
public class OrderProps {

    @Min(value = 5, message = "must be between 5 and 50")
    @Max(value = 50, message = "must be between 5 and 50")
    private int pageSize = 20;
}
