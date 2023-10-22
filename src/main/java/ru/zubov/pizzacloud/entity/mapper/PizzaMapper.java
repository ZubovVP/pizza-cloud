package ru.zubov.pizzacloud.entity.mapper;

import org.mapstruct.*;
import org.springframework.stereotype.Service;
import ru.zubov.pizzacloud.entity.Pizza;
import ru.zubov.pizzacloud.entity.dtos.PizzaDto;

@Service
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PizzaMapper {
    Pizza pizzaDtoToPizza(PizzaDto pizzaDto);

    PizzaDto pizzaToPizzaDto(Pizza pizza);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Pizza updatePizzaFromPizzaDto(PizzaDto pizzaDto, @MappingTarget Pizza pizza);
}
