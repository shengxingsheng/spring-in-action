package org.sxs.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

/**
 * @author sxs
 * @date 9/2/2023 2:20
 */
@Controller
@Slf4j
public class GreetingController {

    @MessageMapping("greeting")
    public Mono<String> handleGreeting(Mono<String> greetingMono) {
        return greetingMono
                .doOnNext(greeting -> {
                    log.info("Received a greeting:{}",greeting);
                })
                .map(greeting->"hello back to you");
    }
}
