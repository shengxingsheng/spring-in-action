package org.sxs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sxs
 * @date 9/9/2023 21:55
 */
@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "holle spring mvc";
    }
}
