package tacos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import tacos.domain.Ingredient;
import tacos.repository.IngredientRepository;


/**
 * @author sxs
 * @date 2023/8/13 16:38
 */
@RequestMapping(path = "/api/ingredients", produces = {"application/json"})
public class IngredientController {
    private final IngredientRepository ingredientRepository;


    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public Flux<Ingredient> allIngredients() {
        return Flux.fromIterable(ingredientRepository.findAll());
    }
}
