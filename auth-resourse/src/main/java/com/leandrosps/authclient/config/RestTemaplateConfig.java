package com.leandrosps.authclient.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemaplateConfig {

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder,
      AuthorizedClientServiceOAuth2AuthorizedClientManager clientManeger) {
    return builder
        .additionalRequestCustomizers(request -> {
          OAuth2AuthorizeRequest auth2AuthorizationRequest = OAuth2AuthorizeRequest
              .withClientRegistrationId("client-server-back").principal("AuthServer")
              .build();
          OAuth2AuthorizedClient authorize = clientManeger.authorize(auth2AuthorizationRequest);
          if (authorize != null) {
            OAuth2AccessToken accessToken = authorize.getAccessToken();
            request.getHeaders().setBearerAuth(accessToken.getTokenValue());
          }
        })
        .build();
  }

}
