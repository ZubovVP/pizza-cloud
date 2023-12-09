package ru.zubov.pizzacloud;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.zubov.pizzacloud.entity.Ingredient;
import ru.zubov.pizzacloud.repository.IngredientRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientByIdConverterTest {
    @InjectMocks
    private IngredientByIdConverter ingredientByIdConverter;

    @Mock
    private IngredientRepository ingredientRepository;

    @Test
    void shouldCorrectlyConvert() {
        //Given
        String id = "id";
        Ingredient ingredient = new Ingredient();
        when(ingredientRepository.findById(id)).thenReturn(Optional.of(ingredient));

        //When
        Ingredient result = ingredientByIdConverter.convert(id);

        //Then
        assertEquals(ingredient, result);
    }

    @Test
    void shouldReturnNullWhenDontFindById() {
        //Given
        String id = "id";
        //When
        Ingredient result = ingredientByIdConverter.convert(id);

        //Then
        assertNull(result);
    }
}