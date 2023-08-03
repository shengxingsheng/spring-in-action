package org.sxs.tacocloud.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.sxs.tacocloud.domain.Ingredient;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author sxs
 * @Date 2023/8/2 20:19
 */
@SpringBootTest
class IngredientRepositoryTest {

    @Autowired
    IngredientRepository ingredientRepository;

    @Test
    public void findById() {
        Optional<Ingredient> flto = ingredientRepository.findById("FLTO");
        assertThat(flto.isPresent()).isTrue();
        assertThat(flto.get()).isEqualTo(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
        long count = ingredientRepository.count();
        assertThat(count).isEqualTo(10);
        Optional<Ingredient> zzzz = ingredientRepository.findById("zzzz");
        assertThat(zzzz.isEmpty()).isTrue();
    }
}