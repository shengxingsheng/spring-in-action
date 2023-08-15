package tacos.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tacos.Ingredient;
import tacos.IngredientRepository;

import java.util.List;

/**
 * @author sxs
 * @date 2023/8/15 20:24
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final IngredientRepository ingredientRepository;
    private final OAuth2AuthorizedClientService clientService;

    @GetMapping("/admin/ingredients")
    public List<Ingredient> findIngredients() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String accessToken = null;
        if (authentication.getClass()
                .isAssignableFrom(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken oauthToken =
                    (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId =
                    oauthToken.getAuthorizedClientRegistrationId();
            if (clientRegistrationId.equals("taco-client")) {
                OAuth2AuthorizedClient client =
                        clientService.loadAuthorizedClient(
                                clientRegistrationId, oauthToken.getName());
                accessToken = client.getAccessToken().getTokenValue();
            }
        }
        log.error("jwt token:" + accessToken);
        return (List<Ingredient>) ingredientRepository.findAll();
    }

    @GetMapping("/")
    public String access(@RegisteredOAuth2AuthorizedClient("taco-client") OAuth2AuthorizedClient authorizedClient,
                         @AuthenticationPrincipal DefaultOidcUser user) {
        log.error("getIdToken" + user.getIdToken());
        log.error("Token" + user.getIdToken().getTokenValue());
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        return accessToken.getTokenValue() + "\n" + user.getIdToken().getTokenValue();
    }
}
