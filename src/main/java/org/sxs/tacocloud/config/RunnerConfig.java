package org.sxs.tacocloud.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.sxs.tacocloud.domain.entity.Ingredient;
import org.sxs.tacocloud.domain.entity.Ingredient.Type;
import org.sxs.tacocloud.domain.entity.Taco;
import org.sxs.tacocloud.domain.entity.TacoOrder;
import org.sxs.tacocloud.domain.entity.User;
import org.sxs.tacocloud.domain.repository.IngredientRepository;
import org.sxs.tacocloud.domain.repository.OrderRepository;
import org.sxs.tacocloud.domain.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 预加载数据
 *
 * @Author sxs
 * @Date 2023/7/31 1:46
 */
@Configuration
public class RunnerConfig {
    @Bean
    public ApplicationRunner dataLoader(IngredientRepository repo, UserRepository userRepository, OrderRepository orderRepository, PasswordEncoder encoder) {
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
            User user = new User("sxs", encoder.encode("123456"), "sheng",
                    "demo", "dmeo", "demo", "1212", "12345678900", "ROLE_USER");
            userRepository.save(user);

            TacoOrder tacoOrder = new TacoOrder();
            List<TacoOrder> orders = new ArrayList<TacoOrder>();
            for (int i = 1; i <= 40; i++) {
                String v = i + "";
                Taco taco = new Taco();
                taco.setName(user.getUsername() + "11");
                taco.addIngredient(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
                TacoOrder order = TacoOrder.builder().ccCVV(v)
                        .ccNumber(v)
                        .deliveryCity(v)
                        .deliveryName(v)
                        .deliveryState(v)
                        .deliveryStreet(v)
                        .deliveryZip(v)
                        .placedAt(new Date(System.currentTimeMillis() + 1000 * i))
                        .ccExpiration("12/23")
                        .tacos(Arrays.asList(taco))
                        .user(user).build();
                orders.add(order);
            }
            orderRepository.saveAll(orders);
        };
    }

}
