package org.sxs.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.rsocket.RSocketRequester;

import java.net.URI;

/**
 * @author sxs
 * @date 9/3/2023 1:50
 */
@Configuration
@Profile("websocket")
@Slf4j
public class WebSocketDevConfig {

    @Bean
    public ApplicationRunner runner(RSocketRequester.Builder builder) {
        return args -> {
            builder.websocket(URI.create("ws://localhost:8080/rsocket"))
                    .route("greeting")
                    .data("hello RSocket")
                    .retrieveMono(String.class)
                    .subscribe(resp -> log.info("Got a response: {}", resp));
        };
    }
}
