package tacos.service;

import tacos.entry.Ingredient;

import java.util.List;

/**
 * @author sxs
 * @date 2023/8/14 21:08
 */

public interface IngredientService {

    List<Ingredient> findAll();
}
