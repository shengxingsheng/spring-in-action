package tacos.service;

import org.springframework.web.client.RestTemplate;
import tacos.entry.Ingredient;

import java.util.Arrays;
import java.util.List;

/**
 * @author sxs
 * @date 2023/8/14 21:09
 */
public class IngredientServiceImpl implements IngredientService {

    private final RestTemplate restTemplate;

    public IngredientServiceImpl(String accessToken) {
        this.restTemplate = new RestTemplate();
        if (accessToken != null) {
            this.restTemplate.getInterceptors().add((request, body, execution) -> {
                request.getHeaders().add("Authorization", "Bearer " + accessToken);
                return execution.execute(request, body);
            });
        }
    }

    @Override
    public List<Ingredient> findAll() {
        return Arrays.asList(restTemplate.getForObject("http://localhost:8080/api/ingredients", Ingredient[].class));
    }
}
