package tacos.restclient;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tacos.Ingredient;

/**
 * @author sxs
 * @date 2023/8/12 20:51
 */
@Service
public class TacoCloudClient {

    private final RestTemplate restTemplate;

    public TacoCloudClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Ingredient getIngredientById(String id) {
        return restTemplate.getForObject("http://localhost:8080/api/ingredients/{id}", Ingredient.class, id);
    }
}
