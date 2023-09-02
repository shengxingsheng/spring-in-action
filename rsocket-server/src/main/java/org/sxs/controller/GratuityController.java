package org.sxs.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.sxs.domain.GratuityIn;
import org.sxs.domain.GratuityOut;
import reactor.core.publisher.Flux;

/**
 * @author sxs
 * @date 9/2/2023 13:36
 */
@Controller
@Slf4j
public class GratuityController {

    @MessageMapping("gratuity")
    public Flux<GratuityOut> calculate(Flux<GratuityIn> gratuityInFlux) {
        return gratuityInFlux
                .doOnNext(in -> log.info("calculate gratuity:{}",in))
                .map(in -> new GratuityOut(in));
    }
}
