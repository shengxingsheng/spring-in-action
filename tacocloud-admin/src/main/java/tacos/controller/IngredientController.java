package tacos.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tacos.entry.Ingredient;
import tacos.service.IngredientService;

import java.util.List;

/**
 * @author sxs
 * @date 2023/8/14 21:20
 */
@RestController
@RequestMapping("/admin/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping
    public List<Ingredient> findAll() {
        return ingredientService.findAll();
    }
}
