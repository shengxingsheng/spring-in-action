package org.sxs.tacocloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.sxs.tacocloud.domain.entity.User;
import org.sxs.tacocloud.domain.repository.UserRepository;

/**
 * @Author sxs
 * @Date 2023/8/4 15:41
 */
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 自定义用户验证
     *
     * @param userRepository
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findUserByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .authorizeHttpRequests(authorizeHttpRequests -> {
                    authorizeHttpRequests
                            .requestMatchers(new AntPathRequestMatcher("/orders"), new AntPathRequestMatcher("/orders/**"), new AntPathRequestMatcher("/design"), new AntPathRequestMatcher("/design/**"))
                            .access(new WebExpressionAuthorizationManager(
                                    "hasRole('ROLE_USER') " +
                                            "&& T(java.time.LocalDate).now().getDayOfWeek().equals(T(java.time.DayOfWeek).SATURDAY)"))
                            .requestMatchers(new AntPathRequestMatcher("/"), AntPathRequestMatcher.antMatcher("/**"))
                            .access(new WebExpressionAuthorizationManager("permitAll()"));
                })
                .formLogin(config -> config.loginPage("/login")
                        .defaultSuccessUrl("/design")
                )
                .csrf(csrfConfigurer -> csrfConfigurer.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                .headers(headers -> headers.frameOptions(config -> config.sameOrigin()))
                .build();
    }
}