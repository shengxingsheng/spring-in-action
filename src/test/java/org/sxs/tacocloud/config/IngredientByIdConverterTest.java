package org.sxs.tacocloud.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sxs.tacocloud.domain.entity.Ingredient;
import org.sxs.tacocloud.domain.repository.IngredientRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @Author sxs
 * @Date 2023/8/2 19:54
 */
class IngredientByIdConverterTest {

    private IngredientByIdConverter converter;

    @BeforeEach
    public void setUp() throws Exception {
        IngredientRepository ingredientRepo = mock(IngredientRepository.class);
        when(ingredientRepo.findById("AAAA"))
                .thenReturn(Optional.of(new Ingredient("AAAA", "TEST INGREDIENT", Ingredient.Type.CHEESE)));
        when(ingredientRepo.findById("ZZZZ"))
                .thenReturn(Optional.empty());
        this.converter = new IngredientByIdConverter(ingredientRepo);
    }

    @Test
    public void shouldReturnValueWhenPresent() {
        assertThat(converter.convert("AAAA"))
                .isEqualTo(new Ingredient("AAAA", "TEST INGREDIENT", Ingredient.Type.CHEESE));
    }

    @Test
    void shouldReturnNullWhenMissing() {
        assertThat(converter.convert("zzzz")).isNull();
    }
}