package tacos.repository;

import org.springframework.data.repository.CrudRepository;
import tacos.domain.Ingredient;

/**
 * @Author sxs
 * @Date 2023/7/29 18:46
 */
public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
