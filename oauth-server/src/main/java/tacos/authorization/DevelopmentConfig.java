package tacos.authorization;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.authorization.users.User;
import tacos.authorization.users.UserRepository;

/**
 * @author sxs
 * @date 2023/8/13 21:45
 */
@Configuration
public class DevelopmentConfig {
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            userRepository.save(new User("test1", encoder.encode("test1"), "ROLE_USER"));
            userRepository.save(new User("test2", encoder.encode("test2"), "ROLE_ADMIN"));
        };
    }
}
