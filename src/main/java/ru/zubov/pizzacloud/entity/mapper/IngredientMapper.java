package ru.zubov.pizzacloud.entity.mapper;

import org.mapstruct.*;
import ru.zubov.pizzacloud.entity.Ingredient;
import ru.zubov.pizzacloud.entity.dtos.IngredientDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IngredientMapper {
    Ingredient ingredientDtoToIngredient(IngredientDto ingredientDto);

    IngredientDto ingredientToIngredientDto(Ingredient ingredient);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Ingredient updateIngredientFromIngredientDto(IngredientDto ingredientDto, @MappingTarget Ingredient ingredient);
}
