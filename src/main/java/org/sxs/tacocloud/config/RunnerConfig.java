package org.sxs.tacocloud.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.sxs.tacocloud.domain.entity.Ingredient;
import org.sxs.tacocloud.domain.entity.Ingredient.Type;
import org.sxs.tacocloud.domain.entity.User;
import org.sxs.tacocloud.domain.repository.IngredientRepository;
import org.sxs.tacocloud.domain.repository.UserRepository;

/**
 * 预加载数据
 *
 * @Author sxs
 * @Date 2023/7/31 1:46
 */
@Configuration
public class RunnerConfig {
    @Bean
    public ApplicationRunner dataLoader(IngredientRepository repo, UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
            repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
            repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
            repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
            repo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
            repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
            repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
            repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
            repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
            repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
            userRepository.save(new User("sxs", encoder.encode("123456"), "sheng",
                    "demo", "dmeo", "demo", "1212", "12345678900"));
        };
    }

}
