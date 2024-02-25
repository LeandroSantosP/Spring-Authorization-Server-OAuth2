package com.leandrosps.authclient.config;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

  @Bean
  @Order(1)
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
      throws Exception {
    return http
        .csrf(r -> r.disable())
        .oauth2ResourceServer(auth -> {
          auth.jwt(jwt -> {
            jwt.jwtAuthenticationConverter(jwtAuthenticationConverter());
          });
        })
        .build();
  }

  private JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
      List<String> roles_authorities = jwt.getClaimAsStringList("authorities");
      if (roles_authorities == null) {
        roles_authorities = Collections.emptyList();
      }
      JwtGrantedAuthoritiesConverter scopesConverter = new JwtGrantedAuthoritiesConverter();
      Collection<GrantedAuthority> scopesAuthorities = scopesConverter.convert(jwt);
      scopesAuthorities
          .addAll(roles_authorities
              .stream()
              .map(SimpleGrantedAuthority::new)
              .toList());
      return scopesAuthorities;
    });
    return jwtAuthenticationConverter;
  }

  @Bean
  public OAuth2AuthorizedClientService auth2AuthorizedClientService(
      ClientRegistrationRepository clientRegistrationRepository) {
    return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
  }

  @Bean
  public AuthorizedClientServiceOAuth2AuthorizedClientManager clientManager(
      ClientRegistrationRepository clientRegistrationRepository,
      OAuth2AuthorizedClientService auth2AuthorizedClientService) {
    var authotizizedClientProvider = OAuth2AuthorizedClientProviderBuilder
        .builder()
        .clientCredentials()
        .build();
    var clientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
        clientRegistrationRepository,
        auth2AuthorizedClientService);
    clientManager.setAuthorizedClientProvider(authotizizedClientProvider);
    return clientManager;
  }
}
