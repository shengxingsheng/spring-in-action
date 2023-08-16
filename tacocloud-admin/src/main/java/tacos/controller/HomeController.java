package tacos.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {

    @GetMapping("/success")
    public String home(@AuthenticationPrincipal DefaultOidcUser user) {
        return "success";
    }

    @GetMapping("/")
    public String access(@RegisteredOAuth2AuthorizedClient("taco-admin-client") OAuth2AuthorizedClient authorizedClient,
                         @AuthenticationPrincipal DefaultOidcUser user,
                         HttpSession session) {
        log.error("getIdToken" + user.getIdToken());
        log.error("Token" + user.getIdToken().getTokenValue());
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        return accessToken.getTokenValue() + "\n" + user.getIdToken().getTokenValue();
    }
//    @GetMapping("/oauth2/authorization/taco-admin-client")
//    public String oauth2() {
//
//        return "";
//    }

}
