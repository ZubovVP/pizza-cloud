package ru.zubov.pizzacloud.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.zubov.pizzacloud.entity.Ingredient;
import ru.zubov.pizzacloud.entity.Ingredient.Type;
import ru.zubov.pizzacloud.entity.Pizza;
import ru.zubov.pizzacloud.entity.PizzaOrder;
import ru.zubov.pizzacloud.entity.dtos.PizzaDto;
import ru.zubov.pizzacloud.entity.dtos.PizzaOrderDto;
import ru.zubov.pizzacloud.entity.mapper.PizzaMapper;
import ru.zubov.pizzacloud.entity.mapper.PizzaOrderMapper;
import ru.zubov.pizzacloud.repository.IngredientRepository;
import ru.zubov.pizzacloud.repository.PizzaRepository;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/design")
@SessionAttributes("pizzaOrder")
public class DesignPizzaController {
    private final IngredientRepository ingredientRepository;
    private final PizzaRepository pizzaRepository;
    private final PizzaMapper pizzaMapper;
    private final PizzaOrderMapper pizzaOrderMapper;

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "pizzaOrder")
    public PizzaOrder order() {
        return new PizzaOrder();
    }

    @ModelAttribute(name = "pizza")
    public Pizza taco() {
        return new Pizza();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processPizza(@Valid PizzaDto pizzaDto, Errors errors, @ModelAttribute PizzaOrderDto pizzaOrderDto) {
        if (errors.hasErrors()) {
            return "design";
        }
        Pizza pizza = pizzaMapper.pizzaDtoToPizza(pizzaDto);
        pizzaRepository.save(pizza);
        PizzaOrder order = pizzaOrderMapper.pizzaOrderDtoToPizzaOrder(pizzaOrderDto);
        order.addPizza(pizza);
        log.info("Processing pizza: {}", pizza);

        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
