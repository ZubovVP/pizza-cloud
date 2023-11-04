package ru.zubov.pizzacloud.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.zubov.pizzacloud.entity.Ingredient;
import ru.zubov.pizzacloud.entity.SignUpDto;
import ru.zubov.pizzacloud.entity.dtos.IngredientDto;
import ru.zubov.pizzacloud.entity.mapper.IngredientMapper;
import ru.zubov.pizzacloud.repository.IngredientRepository;
import ru.zubov.pizzacloud.service.CustomUserDetailsService;

import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/ingredients", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientRepository repo;
    private final IngredientMapper ingredientMapper;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping
    public Iterable<IngredientDto> allIngredients() {
        return repo.findAll().stream()
                .map(ingredientMapper::ingredientToIngredientDto)
                .collect(Collectors.toList());
    }

    @PostMapping
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Ingredient saveIngredient(@RequestBody IngredientDto ingredient) {
        return repo.save(ingredientMapper.ingredientDtoToIngredient(ingredient));
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteIngredient(@PathVariable("id") String ingredientId, @RequestBody SignUpDto signUpDto) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(signUpDto.getLogin());
        if(userDetails == null){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        repo.deleteById(ingredientId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
