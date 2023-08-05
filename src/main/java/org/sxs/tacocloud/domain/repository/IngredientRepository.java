package org.sxs.tacocloud.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.sxs.tacocloud.domain.entity.Ingredient;

/**
 * @Author sxs
 * @Date 2023/7/29 18:46
 */
public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
