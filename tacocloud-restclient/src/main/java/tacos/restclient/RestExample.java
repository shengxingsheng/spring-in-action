package tacos.restclient;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import tacos.Ingredient;

/**
 * @author sxs
 * @date 2023/8/12 23:54
 */

@ComponentScan
@Slf4j
public class RestExample {

    public static void main(String[] args) {
        SpringApplication.run(RestExample.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        //启用HAL支持
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));

        restTemplate.getMessageConverters().add(converter);
        return restTemplate;

    }

    //    @Bean

    @Bean
    public CommandLineRunner fetchIngredients(TacoCloudClient tacoCloudClient) {
        return args -> {
            Ingredient ched = tacoCloudClient.getIngredientById("CHED");
            log.info(ched.toString());
        };
    }

}
