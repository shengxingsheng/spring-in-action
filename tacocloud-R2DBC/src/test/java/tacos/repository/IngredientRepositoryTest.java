package tacos.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import tacos.domain.Ingredient;
import tacos.domain.Ingredient.Type;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author sxs
 * @date 9/1/2023 11:37
 */

@DataR2dbcTest
class IngredientRepositoryTest {

    @Autowired
    IngredientRepository ingredientRepo;

    @BeforeEach
    void setUp() throws Exception {
        Flux<Ingredient> initFlux = ingredientRepo.deleteAll()
                .thenMany(ingredientRepo.saveAll(
                        Flux.just(
                                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                                new Ingredient("CHED", "Cheddar Cheese", Type.CHEESE))
                ));
        StepVerifier.create(initFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void shouldSaveAndFetchIngredients() throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        StepVerifier.create(ingredientRepo.findAll())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(x -> true)
                .consumeRecordedWith(ingredients -> {
                    assertThat(ingredients).hasSize(3);
                    System.out.println(Thread.currentThread().getName());
                    assertThat(ingredients).contains(
                            new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
                    assertThat(ingredients).contains(
                            new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
                    assertThat(ingredients).contains(
                            new Ingredient("CHED", "Cheddar Cheese", Type.CHEESE));
                })
                .verifyComplete();
        StepVerifier.create(ingredientRepo.findBySlug("FLTO"))
                .assertNext(ingredient -> {
                    System.out.println(Thread.currentThread().getName());
                    ingredient.equals(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
                }).verifyComplete();
    }

}