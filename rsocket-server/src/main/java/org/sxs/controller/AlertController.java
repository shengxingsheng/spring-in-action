package org.sxs.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.sxs.domain.Alert;
import reactor.core.publisher.Mono;

/**
 * @author sxs
 * @date 9/2/2023 11:53
 */
@Controller
@Slf4j
public class AlertController {
    @MessageMapping("alert")
    public Mono<Void> setAlert(Mono<Alert> alertMono) {

        return alertMono.flatMap(alert -> {
            log.info("{} alert ordered by {} at {}",
                    alert.getLevel(),
                    alert.getOrderedBy(),
                    alert.getOrderedAt());
            return Mono.empty();
        });

    }
}
