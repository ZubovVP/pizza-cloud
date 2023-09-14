package ru.zubov.pizzacloud;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.zubov.pizzacloud.entity.Ingredient;
import ru.zubov.pizzacloud.repository.IngredientRepository;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private IngredientRepository ingredientRepository;

    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.findById(id).orElse(null);
    }
}
