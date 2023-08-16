package tacos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author sxs
 * @date 2023/8/16 16:01
 */
@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout() {
        return "home";
    }
}
