package ru.zubov.pizzacloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.zubov.pizzacloud.entity.Ingredient;
import ru.zubov.pizzacloud.entity.Ingredient.Type;
import ru.zubov.pizzacloud.entity.Pizza;
import ru.zubov.pizzacloud.entity.PizzaOrder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("pizzaOrder")
public class DesignPizzaController {
    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("SIZE25", "25 cm", Type.SIZE),
                new Ingredient("SIZE32", "32 cm", Type.SIZE),
                new Ingredient("SIZE40", "40 cm", Type.SIZE),

                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("BEEF", "Beef", Type.PROTEIN),
                new Ingredient("CHKN", "Chicken", Type.PROTEIN),
                new Ingredient("SAUG", "Sausage", Type.PROTEIN),

                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("SWPE", "Sweet Pepper", Type.VEGGIES),

                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),

                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("TMSC", "Tomato Sauce", Type.SAUCE),
                new Ingredient("BBQ", "BBQ", Type.SAUCE)
        );
        Type[] types = Type.values();
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
    public String processPizza(Pizza pizza, @ModelAttribute PizzaOrder pizzaOrder) {
        pizzaOrder.addPizza(pizza);
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
