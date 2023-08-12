package tacos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author sxs
 * @Date 2023/7/28
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * test
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login");
        registry.addViewController("/admin");
    }
}
