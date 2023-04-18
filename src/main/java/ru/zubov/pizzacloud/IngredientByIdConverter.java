package ru.zubov.pizzacloud;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.zubov.pizzacloud.entity.Ingredient;

import java.util.HashMap;
import java.util.Map;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private Map<String, Ingredient> ingredientMap = new HashMap<>();

    public IngredientByIdConverter() {
        ingredientMap.put("SIZE25", new Ingredient("SIZE25", "25 cm", Ingredient.Type.SIZE));
        ingredientMap.put("SIZE32", new Ingredient("SIZE32", "32 cm", Ingredient.Type.SIZE));
        ingredientMap.put("SIZE40", new Ingredient("SIZE40", "40 cm", Ingredient.Type.SIZE));
        ingredientMap.put("GRBF", new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
        ingredientMap.put("BEEF", new Ingredient("BEEF", "Beef", Ingredient.Type.PROTEIN));
        ingredientMap.put("CHKN", new Ingredient("CHKN", "Chicken", Ingredient.Type.PROTEIN));
        ingredientMap.put("SAUG", new Ingredient("SAUG", "Sausage", Ingredient.Type.PROTEIN));
        ingredientMap.put("TMTO", new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
        ingredientMap.put("LETC", new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
        ingredientMap.put("SWPE", new Ingredient("SWPE", "Sweet Pepper", Ingredient.Type.VEGGIES));
        ingredientMap.put("CHED", new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
        ingredientMap.put("JACK", new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
        ingredientMap.put("SLSA", new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
        ingredientMap.put("TMSC", new Ingredient("TMSC", "Tomato Sauce", Ingredient.Type.SAUCE));
        ingredientMap.put("BBQ", new Ingredient("BBQ", "BBQ", Ingredient.Type.SAUCE));
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientMap.get(id);
    }
}
