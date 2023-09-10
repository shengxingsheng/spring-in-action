package org.sxs;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.notify.Notifier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @author sxs
 * @date 9/10/2023 10:40
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AdminServerProperties adminServer;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .pathMatchers(this.adminServer.path("/assets/**"))
                        .permitAll()
                        .pathMatchers("  **")
                        .permitAll()
                        .pathMatchers(this.adminServer.path("/login"))
                        .permitAll()
                        .anyExchange()
                        .authenticated())
                .formLogin(formLoginSpec -> formLoginSpec
                        .loginPage(this.adminServer.path("/login"))
                        .authenticationSuccessHandler(loginSuccessHandler(this.adminServer.path("/"))))
                .logout(logoutSpec -> logoutSpec
                        .logoutUrl(this.adminServer.path("/logout"))
                        .logoutSuccessHandler(logoutSuccessHandler(this.adminServer.path("/login?logout"))))
                .httpBasic(Customizer.withDefaults())
                .csrf(csrfSpec -> csrfSpec.disable())
                .build();
    }

    private ServerLogoutSuccessHandler logoutSuccessHandler(String uri) {
        RedirectServerLogoutSuccessHandler handler = new RedirectServerLogoutSuccessHandler();
        handler.setLogoutSuccessUrl(URI.create(uri));
        return handler;
    }

    private ServerAuthenticationSuccessHandler loginSuccessHandler(String uri) {
        RedirectServerAuthenticationSuccessHandler handler = new RedirectServerAuthenticationSuccessHandler();
        handler.setLocation(URI.create(uri));
        return handler;

    }
    @Bean
    public Notifier notifier() {
        return (e) -> Mono.empty();
    }

}
