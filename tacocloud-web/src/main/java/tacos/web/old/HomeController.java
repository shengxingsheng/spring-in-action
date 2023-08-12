package tacos.web.old;

import org.springframework.web.bind.annotation.GetMapping;
import org.sxs.tacocloud.config.WebConfig;

/**
 * @author sxs
 * @since 2023/7/22
 * @deprecated 已作废, 使用了ViewController简化, 请查看 {@link WebConfig}
 */
@Deprecated(since = "4.2", forRemoval = true)
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

}
