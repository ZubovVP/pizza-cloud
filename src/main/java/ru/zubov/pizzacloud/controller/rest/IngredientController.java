package ru.zubov.pizzacloud.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.zubov.pizzacloud.entity.SignUpDto;
import ru.zubov.pizzacloud.entity.dtos.IngredientDto;
import ru.zubov.pizzacloud.entity.mapper.IngredientMapper;
import ru.zubov.pizzacloud.repository.IngredientRepository;
import ru.zubov.pizzacloud.service.CustomUserDetailsService;

import java.util.Map;
import java.util.Objects;

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
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveIngredient(@RequestBody Map<String, Object> map) {
        SignUpDto signUpDto = (SignUpDto) map.get("signUpDto");
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(signUpDto.getLogin());
        if (userDetails == null) {
            return new ResponseEntity<>("Username can't find!", HttpStatus.BAD_REQUEST);
        }
        if (passwordEncoder.matches(signUpDto.getPassword(), userDetails.getPassword())) {
            if (userDetails.getAuthorities().stream().anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_ADMIN"))) {
                return new ResponseEntity<>(repo.save(ingredientMapper.ingredientDtoToIngredient(((IngredientDto) map.get("ingredient")))), HttpStatus.OK);
            }
            return new ResponseEntity<>("Don't have right role!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Username or password is wrong!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable("id") String ingredientId, @RequestBody SignUpDto signUpDto) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(signUpDto.getLogin());
        if (userDetails == null) {
            return new ResponseEntity<>("Username can't find!", HttpStatus.BAD_REQUEST);

        }
        if (passwordEncoder.matches(signUpDto.getPassword(), userDetails.getPassword())) {
            if (userDetails.getAuthorities().stream().anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_ADMIN"))) {
                repo.deleteById(ingredientId);
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            return new ResponseEntity<>("Don't have right role!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Username or password is wrong!", HttpStatus.BAD_REQUEST);
    }
}
