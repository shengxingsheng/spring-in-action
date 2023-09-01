package tacos.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import tacos.domain.Ingredient;

/**
 * @author sxs
 * @date 9/1/2023 11:31
 */
public interface IngredientRepository extends ReactiveCrudRepository<Ingredient,Long> {
    Mono<Ingredient> findBySlug(String slug);
}
