package tacos;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.domain.Ingredient;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * 测试WebClient(类似RestTemplate)
 * @author sxs
 * @date 8/31/2023 16:43
 */
public class WebClientTest {

    private WebClient webClient = WebClient.create("http://localhost:8080");
    @Test
    void testWebClient() throws InterruptedException {
        Flux<Ingredient> flux = webClient
                .get()
                .uri("/api/ingredients")
                .retrieve()
                .bodyToFlux(Ingredient.class)
                .take(5);
        flux.subscribe(System.out::println);
        Thread.sleep(1000);
    }

    @Test
    void subscribeTimeOut() throws InterruptedException {
        Flux<Ingredient> flux = webClient
                .get()
                .uri("/api/ingredients")
                .retrieve()
                .bodyToFlux(Ingredient.class)
                .delayElements(Duration.ofSeconds(1))
                .take(5);
        flux.timeout(Duration.ofSeconds(1))
                .subscribe(System.out::println,e-> System.out.println(e.getMessage()));
        Thread.sleep(5000);
    }
    @Test
    void post() throws InterruptedException {
        Ingredient ingredient = new Ingredient("b","b", Ingredient.Type.WRAP);
        Mono<Ingredient> mono = webClient.post()
                .uri("/api/ingredients")
                .bodyValue(ingredient)
                .retrieve()
                .bodyToMono(Ingredient.class);
        mono.subscribe(System.out::println);
        Thread.sleep(5000);
    }

    @Test
    void error() throws InterruptedException {

        Mono<Ingredient> mono = webClient.post()
                .uri("/api/ingredients/1/1")
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode== HttpStatus.NOT_FOUND, clientResponse -> Mono.just(new ResourcesNotFoundException()))
                .bodyToMono(Ingredient.class);
        mono.subscribe(System.out::println);
        Thread.sleep(5000);
    }

    private static class ResourcesNotFoundException extends Exception {
        @Override
        public String getMessage() {
            return "404---";
        }
    }

    @Test
    void exchange() throws InterruptedException {
        Flux<Object> flux = webClient.get()
                .uri("/api/ingredients")
                .exchangeToFlux(clientResponse -> {
                    Optional<MediaType> mediaType = clientResponse.headers().contentType();
                    System.out.println(mediaType.get());
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToFlux(Ingredient.class);
                    }else {
                        return clientResponse.createError().flux();
                    }
                });
        flux.subscribe(System.out::println);
        Thread.sleep(5000);
    }
}
