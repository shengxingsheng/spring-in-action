package tacos.domain;


import lombok.*;
import org.springframework.data.annotation.Id;

/**
 * @author sxs
 * @date 9/1/2023 1:06
 */
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Ingredient {

    @Id
    @EqualsAndHashCode.Exclude
    private Long id;
    private @NonNull String slug;
    private @NonNull String name;

    private @NonNull Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

    public static void main(String[] args) {
        Ingredient ingredient = new Ingredient("a", "a", Type.WRAP);
        System.out.println(ingredient);
    }
}
