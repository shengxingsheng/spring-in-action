package tacos.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.domain.entity.Ingredient;
import tacos.domain.entity.Ingredient.Type;
import tacos.domain.entity.Taco;
import tacos.domain.entity.TacoOrder;
import tacos.domain.entity.User;
import tacos.domain.repository.IngredientRepository;
import tacos.domain.repository.TacoOrderRepository;
import tacos.domain.repository.UserRepository;

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
    @Profile("prod")
    public ApplicationRunner test() {
        System.out.println("生产");
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
            }
        };
    }

    @Bean
    public ApplicationRunner dataLoader(IngredientRepository repo, UserRepository userRepository, TacoOrderRepository tacoOrderRepository, PasswordEncoder encoder) {
        return args -> {
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
            repo.saveAll(list);
            User user = new User("sxs", encoder.encode("123456"), "sheng",
                    "demo", "dmeo", "demo", "1212", "12345678900", "ROLE_USER");
            userRepository.save(user);

            TacoOrder tacoOrder = new TacoOrder();
            List<TacoOrder> orders = new ArrayList<TacoOrder>();
            for (int i = 1; i <= 40; i++) {
                String v = i + "";
                Taco taco = new Taco();
                taco.setCreatedAt(new Date(System.currentTimeMillis() + 1000 * i));
                taco.setName(user.getUsername() + "_" + i);
                taco.addIngredient(list.get(i % Type.values().length));
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
            tacoOrderRepository.saveAll(orders);
        };
    }

}
