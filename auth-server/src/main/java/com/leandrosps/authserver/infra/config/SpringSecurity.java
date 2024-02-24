package com.leandrosps.authserver.infra.config;

import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import com.leandrosps.authserver.application.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

        @Autowired
        private CustomAuthenticationProvider customAuthenticationProvider;

        @Autowired
        public void bindAuthenticationProvider(AuthenticationManagerBuilder authenticationManagerBuilder) {
                authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
        }

        @Bean
        RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {

                RegisteredClient awuserclient = RegisteredClient
                                .withId(UUID.randomUUID().toString())
                                .clientId("client-server-back")
                                .clientSecret(passwordEncoder.encode("secret"))
                                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                                .scope("users:read")
                                .scope("users:write")
                                .tokenSettings(TokenSettings.builder()
                                                .accessTokenTimeToLive(Duration.ofMinutes(5))
                                                .build())
                                .clientSettings(ClientSettings.builder()
                                                .requireAuthorizationConsent(false)
                                                .build())
                                .build();

                RegisteredClient awblogClient = RegisteredClient
                                .withId(UUID.randomUUID().toString())
                                .clientId("client-server-front")
                                .clientName("ClientServer")
                                .clientSecret(passwordEncoder.encode("secret"))
                                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                                .redirectUri("http://oidcdebugger.com/debug")
                                .redirectUri("https://oauth.pstmn.io/v1/callback")
                                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/client-server-front")
                                .scope("users:read")
                                .scope("users:write")
                                .tokenSettings(TokenSettings.builder()
                                                .accessTokenTimeToLive(Duration.ofMinutes(15))
                                                .refreshTokenTimeToLive(Duration.ofDays(1))
                                                .reuseRefreshTokens(false)
                                                .build())
                                .clientSettings(ClientSettings.builder()
                                                .requireAuthorizationConsent(true)
                                                .build())
                                .build();

                return new InMemoryRegisteredClientRepository(awuserclient, awblogClient);
        }

        @Bean
        public AuthorizationServerSettings authorizationServerSettings(AuthProperties authProperties) {

                return AuthorizationServerSettings.builder()
                                .issuer(authProperties.getProviderUri())
                                .build();
        }

}
