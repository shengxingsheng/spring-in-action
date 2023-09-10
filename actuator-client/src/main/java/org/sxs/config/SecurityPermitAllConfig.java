package org.sxs.config;

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityPermitAllConfig {
    @Bean
    protected SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        return http
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .matchers(EndpointRequest.toAnyEndpoint())
                        .authenticated()
                        .anyExchange()
                        .permitAll())
                .csrf(csrfSpec -> csrfSpec.disable())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}