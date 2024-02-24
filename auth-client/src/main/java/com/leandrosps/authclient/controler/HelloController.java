package com.leandrosps.authclient.controler;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HelloController {

    @GetMapping("/token")
    public String getMethodName(
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client,
            @AuthenticationPrincipal OidcUser oidcUser) {
        return ("""
                <h3> Access Token: %s </h3>
                <h3> Refresh Token: %s </h3>
                <h3> Id Token: %s </h3>
                <h3> Claims: %s </h3>
                  """.formatted(client.getAccessToken().getTokenValue(),
                client.getRefreshToken().getTokenValue(),
                oidcUser.getIdToken().getTokenValue(),
                oidcUser.getClaims()));
    }

}
