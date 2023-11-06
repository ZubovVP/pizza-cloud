package ru.zubov.pizzacloud.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.zubov.pizzacloud.entity.Ingredient;
import ru.zubov.pizzacloud.entity.SignUpDto;
import ru.zubov.pizzacloud.entity.dtos.IngredientDto;
import ru.zubov.pizzacloud.entity.mapper.IngredientMapper;
import ru.zubov.pizzacloud.repository.IngredientRepository;
import ru.zubov.pizzacloud.service.CustomUserDetailsService;

import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/ingredients", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientRepository repo;
    private final IngredientMapper ingredientMapper;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

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
        if (userDetails == null) {
            return new ResponseEntity<>("Username can't find!", HttpStatus.BAD_REQUEST);

        }

        if (!Objects.equals(userDetails.getPassword(), passwordEncoder.encode(signUpDto.getPassword()))) {
            return new ResponseEntity<>("Username or password is wrong!", HttpStatus.BAD_REQUEST);
        }

        if (userDetails.getAuthorities().stream().anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_ADMIN"))) {
            repo.deleteById(ingredientId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>("Don't have right role!", HttpStatus.BAD_REQUEST);
    }
}
