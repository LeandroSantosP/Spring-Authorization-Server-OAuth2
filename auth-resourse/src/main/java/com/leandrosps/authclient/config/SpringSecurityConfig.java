package com.leandrosps.authclient.config;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
      throws Exception {

    return http
        .authorizeHttpRequests(request -> request.anyRequest().authenticated())
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
        return Collections.emptyList();
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
}
