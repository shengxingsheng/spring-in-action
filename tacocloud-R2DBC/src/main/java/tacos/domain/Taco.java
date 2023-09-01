package tacos.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

/**
 * @author sxs
 * @date 9/1/2023 1:34
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Taco {

    @Id
    private Long id;

    private @NonNull String name;

    private @NonNull Set<Long> ingredientIds = new HashSet<Long>();


    private void addIngredient(Ingredient ingredient) {
        ingredientIds.add(ingredient.getId());
    }

}
