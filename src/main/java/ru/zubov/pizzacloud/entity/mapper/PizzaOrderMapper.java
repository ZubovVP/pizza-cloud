package ru.zubov.pizzacloud.entity.mapper;

import org.mapstruct.*;
import ru.zubov.pizzacloud.entity.PizzaOrder;
import ru.zubov.pizzacloud.entity.dtos.PizzaOrderDto;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE
        , componentModel = "spring",
        uses = {UserMapper.class, PizzaMapper.class}
)
public interface PizzaOrderMapper {
    PizzaOrder pizzaOrderDtoToPizzaOrder(PizzaOrderDto pizzaOrderDto);

    PizzaOrderDto pizzaOrderToPizzaOrderDto(PizzaOrder pizzaOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PizzaOrder updatePizzaOrderFromPizzaOrderDto(PizzaOrderDto pizzaOrderDto, @MappingTarget PizzaOrder pizzaOrder);
}
