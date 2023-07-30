package org.sxs.tacocloud.repository;

import org.sxs.tacocloud.domain.Ingredient;

import java.util.List;
import java.util.Optional;

/**
 * @Author sxs
 * @Date 2023/7/29 18:46
 */
public interface IngredientRepository {
    List<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);
}
