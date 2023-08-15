package tacos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/success")
    public String home() {
        return "success";
    }


//    @GetMapping("/oauth2/authorization/taco-admin-client")
//    public String oauth2() {
//
//        return "";
//    }

}
