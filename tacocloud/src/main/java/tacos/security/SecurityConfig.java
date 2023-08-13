package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import tacos.User;
import tacos.UserRepository;

import java.util.*;

/**
 * @Author sxs
 * @Date 2023/8/4 15:41
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        String encodingId = "argon2@v1";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2@SpringSecurity_v5_8", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("scrypt@SpringSecurity_v5_8", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("argon2@SpringSecurity_v5_8", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("argon2@v1", new Argon2PasswordEncoder(32, 64, 4, 1 << 16, 15));
        return new DelegatingPasswordEncoder(encodingId, encoders);
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
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return userRequest -> {
            OAuth2User oAuth2User = delegate.loadUser(userRequest);
            OAuth2AccessToken accessToken = userRequest.getAccessToken();
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            // TODO
            System.out.println("scopes:" + accessToken.getScopes());
            System.out.println("getTokenType:" + accessToken.getTokenType().toString());
            System.out.println("TokenValue:" + accessToken.getTokenValue());
            System.out.println("getExpiresAt:" + accessToken.getExpiresAt().toString());
            System.out.println("getIssuedAt:" + accessToken.getIssuedAt().toString());
            Collection<? extends GrantedAuthority> authorities = oAuth2User.getAuthorities();
            System.out.println(oAuth2User.toString());
            mappedAuthorities.add(() -> "ROLE_USER");
            DefaultOAuth2User user = new DefaultOAuth2User(mappedAuthorities, oAuth2User.getAttributes(), "login");
            System.out.println(user);

            return user;
//            return oAuth2User;

        };
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .authorizeHttpRequests(authorizeHttpRequests -> {
                    authorizeHttpRequests
                            .requestMatchers(new AntPathRequestMatcher("/admin"), new AntPathRequestMatcher("/admin/**"))
                            .access(new WebExpressionAuthorizationManager("hasRole('ROLE_ADMIN')"))
                            .requestMatchers(new AntPathRequestMatcher("/orders"), new AntPathRequestMatcher("/orders/**"), new AntPathRequestMatcher("/design"), new AntPathRequestMatcher("/design/**"))
                            .access(new WebExpressionAuthorizationManager("hasRole('ROLE_USER')"))
                            .requestMatchers(new AntPathRequestMatcher("/"), AntPathRequestMatcher.antMatcher("/**"))
                            .access(new WebExpressionAuthorizationManager("permitAll()"));
                })
                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults())
                .logout(config -> config.logoutSuccessUrl("/"))
                .csrf(csrfConfigurer -> csrfConfigurer
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"), new AntPathRequestMatcher("/api/**"))
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .headers(headers -> headers.frameOptions(config -> config.sameOrigin()))
                .build();
    }
}