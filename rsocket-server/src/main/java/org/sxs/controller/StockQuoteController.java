package org.sxs.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.sxs.domain.StockQuote;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

/**
 * @author sxs
 * @date 9/2/2023 11:25
 */
@Controller
@Slf4j
public class StockQuoteController {

    @MessageMapping("stock/{symbol}")
    public Flux<StockQuote> getStockPrice(@DestinationVariable("symbol") String symbol) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(i->{
                    BigDecimal price = BigDecimal.valueOf(Math.random() * 10);
                    return new StockQuote(symbol,price , Instant.now());
                });
    }

}
