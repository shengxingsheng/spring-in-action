package org.sxs.controller;

/**
 * @author sxs
 * @date 9/9/2023 21:21
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HomeController {

    @GetMapping("/")
    public Mono<String> home() {
        return Mono.just("holle");
    }

}
