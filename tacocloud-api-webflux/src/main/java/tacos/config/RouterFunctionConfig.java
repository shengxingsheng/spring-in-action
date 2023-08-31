package tacos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.domain.Ingredient;
import tacos.repository.IngredientRepository;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author sxs
 * @date 8/31/2023 10:54
 */
@Configuration
public class RouterFunctionConfig {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Bean
    public RouterFunction helloRouterFunction() {
        return route(GET("/hello"),
                request -> ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just("{\"name\":\"sxs\",\"age\":18}"), String.class))
                .andRoute(GET("/hello1"),
                        request -> ok().body(Mono.just("hello1"), String.class));
    }
    @Bean
    public RouterFunction ingredientRouter() {
        return route(GET("/api/ingredients"), this::findAll)
                .andRoute(POST("/api/ingredients"), this::postIngredients)
                .andRoute(GET("/api/ingredients/{id}"), this::findById)
                .andRoute(GET("/"),request -> ok().bodyValue("home"));
    }

    private Mono<ServerResponse> findById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return ServerResponse.ok().body(Mono.just(ingredientRepository.findById(id)), Ingredient.class);
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok().body(Flux.fromIterable(ingredientRepository.findAll()), Ingredient.class);
    }
    public Mono<ServerResponse> postIngredients(ServerRequest request) {
        return request.bodyToMono(Ingredient.class)
                .flatMap(ingredient->Mono.just(ingredientRepository.save(ingredient)))
                .flatMap(ingredient-> {
                    try {
                        return ServerResponse.created(new URI("http://localhost:8080/api/ingredients/"+ingredient.getId()))
                                .body(Mono.just(ingredient),Ingredient.class);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
