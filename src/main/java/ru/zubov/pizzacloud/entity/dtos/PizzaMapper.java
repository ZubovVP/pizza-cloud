package ru.zubov.pizzacloud.entity.dtos;

import org.mapstruct.*;
import ru.zubov.pizzacloud.entity.Pizza;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "cdi")
public interface PizzaMapper {
    Pizza pizzaDtoToPizza(PizzaDto pizzaDto);

    PizzaDto pizzaToPizzaDto(Pizza pizza);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Pizza updatePizzaFromPizzaDto(PizzaDto pizzaDto, @MappingTarget Pizza pizza);
}
