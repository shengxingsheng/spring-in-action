package tacos.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tacos.Ingredient;
import tacos.IngredientRepository;

/**
 * @author sxs
 * @date 2023/8/13 16:38
 */
@RestController
@RequestMapping(path = "/api/ingredients", produces = {"application/json"})
public class IngredientController {
    private final IngredientRepository ingredientRepository;


    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public Iterable<Ingredient> allIngredients() {
        return ingredientRepository.findAll();
    }
}
