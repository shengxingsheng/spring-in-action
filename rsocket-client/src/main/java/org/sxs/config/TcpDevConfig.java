package org.sxs.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.sxs.domain.Alert;
import org.sxs.domain.GratuityIn;
import org.sxs.domain.GratuityOut;
import org.sxs.domain.StockQuote;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author sxs
 * @date 9/2/2023 10:25
 */
@Configuration
@Profile("tcp")
@Slf4j
public class TcpDevConfig {

    @Bean
    public ApplicationRunner requestResponse(RSocketRequester.Builder builder) {
        return args -> {
            RSocketRequester tcp = builder.tcp("127.0.0.1", 7000);
            tcp.route("greeting")
                    .data("hello 7000")
                    .retrieveMono(String.class)
                    .subscribe(resp -> log.info("Got a response:{}", "response"));
        };
    }
    @Bean
    public ApplicationRunner requestStream(RSocketRequester.Builder builder) {
        return args -> {
            RSocketRequester tcp = builder.tcp("127.0.0.1", 7000);
            tcp.route("stock/{symbol}","xyz")
                    .retrieveFlux(StockQuote.class)
                    .doOnNext(stockQuote -> log.info("Price of {} : {} (at {})",
                            stockQuote.getSymbol(),
                            stockQuote.getPrice(),
                            stockQuote.getTimestamp()))
                    .subscribe();
        };
    }

    @Bean
    public ApplicationRunner fireAndForget(RSocketRequester.Builder builder) {
        return args -> {
            builder.tcp("127.0.0.1", 7000)
                    .route("alert")
                    .data(new Alert(Alert.Level.RED,"Craig", Instant.now()))
                    .send()
                    .subscribe();
        };
    }

    @Bean
    public ApplicationRunner channel(RSocketRequester.Builder builder) {
        Flux<GratuityIn> inFlux = Flux.fromArray(new GratuityIn[]{
                new GratuityIn(BigDecimal.valueOf(35.50), 18),
                new GratuityIn(BigDecimal.valueOf(10.00), 15),
                new GratuityIn(BigDecimal.valueOf(23.25), 20),
                new GratuityIn(BigDecimal.valueOf(52.75), 18),
                new GratuityIn(BigDecimal.valueOf(80.00), 15)
        });

        return args -> {
            builder.tcp("127.0.0.1", 7000)
                    .route("gratuity")
                    .data(inFlux)
                    .retrieveFlux(GratuityOut.class)
                    .subscribe(out->{
                        log.info(out.getPercent() + "% gratuity on "
                                + out.getBillTotal() + " is "
                                + out.getGratuity());
                    });
        };
    }
}
