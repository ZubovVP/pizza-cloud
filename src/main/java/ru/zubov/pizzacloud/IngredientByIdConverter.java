package ru.zubov.pizzacloud;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.zubov.pizzacloud.entity.Ingredient;
import ru.zubov.pizzacloud.repository.IngredientRepository;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private final IngredientRepository ingredientRepository;

    public IngredientByIdConverter(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.findById(id).orElse(null);
    }
}
