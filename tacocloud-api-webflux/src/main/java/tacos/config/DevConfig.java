package tacos.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.domain.Ingredient;
import tacos.domain.Ingredient.Type;
import tacos.domain.Users;
import tacos.repository.IngredientRepository;
import tacos.repository.UsersRepository;

import java.util.Arrays;
import java.util.List;

/**
 * @author sxs
 * @date 8/31/2023 2:38
 */
@Configuration
public class DevConfig {


    @Bean
    public ApplicationRunner applicationRunner(IngredientRepository ingredientRepository, UsersRepository usersRepository, PasswordEncoder encoder) {

        return arg->{
            List<Ingredient> list = Arrays.asList(
                    new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                    new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                    new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                    new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                    new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                    new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                    new Ingredient("CHED", "Cheddar", Type.CHEESE),
                    new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                    new Ingredient("SLSA", "Salsa", Type.SAUCE),
                    new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
            );
            ingredientRepository.saveAll(list);
            List<Users> users = Arrays.asList(new Users("sxs", encoder.encode("123456")));
            usersRepository.saveAll(users);
        };

    }
}
