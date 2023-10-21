package ru.zubov.pizzacloud.entity.dtos;

import org.mapstruct.*;
import ru.zubov.pizzacloud.entity.PizzaOrder;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "cdi")
public interface PizzaOrderMapper {
    PizzaOrder pizzaOrderDtoToPizzaOrder(PizzaOrderDto pizzaOrderDto);

    PizzaOrderDto pizzaOrderToPizzaOrderDto(PizzaOrder pizzaOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PizzaOrder updatePizzaOrderFromPizzaOrderDto(PizzaOrderDto pizzaOrderDto, @MappingTarget PizzaOrder pizzaOrder);
}
