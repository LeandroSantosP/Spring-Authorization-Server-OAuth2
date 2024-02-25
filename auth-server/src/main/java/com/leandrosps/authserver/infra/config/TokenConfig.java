package com.leandrosps.authserver.infra.config;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import com.leandrosps.authserver.infra.exceptions.CustomError;
import com.leandrosps.authserver.infra.repositories.UserRepository;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class TokenConfig {
    @Bean
    public JWKSet jwkSet(AuthProperties authProperties) throws Exception {
        var jskProps = authProperties.getJks();
        final InputStream inputStream = new ClassPathResource(jskProps.getPath()).getInputStream();
        final KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(inputStream, jskProps.getKeypass().toCharArray());
        RSAKey rsaKey = RSAKey.load(keyStore, jskProps.getAlias(),
                jskProps.getStorepass().toCharArray());
        return new JWKSet(rsaKey);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(JWKSet jwkSet) {
        return ((jwkSelector, securityContext) -> jwkSelector.select(jwkSet));
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtEncodedConstomize(UserRepository userRepository) {
        return (context -> {
            Authentication authentication = context.getPrincipal();
            if (authentication.getPrincipal() instanceof User) {
                final User userData = (User) authentication.getPrincipal();
                Set<String> authoritis = new HashSet<>();
                System.out.println(userData.getAuthorities());
                for (GrantedAuthority authorities : userData.getAuthorities()) {
                    authoritis.add(authorities.getAuthority());
                }
                final var userOp = userRepository.getByEmail(userData.getUsername());
                if (userOp.isEmpty()) {
                    throw new CustomError("User not found");
                }
                var user = userOp.get();
                context.getClaims().claim("user_id", user.getId());
                context.getClaims().claim("user_username", user.getUsername());
                context.getClaims().claim("authorities", authoritis);
            }
        });
    }
}
