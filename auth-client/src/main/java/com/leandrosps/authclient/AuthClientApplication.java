package com.leandrosps.authclient;

import com.leandrosps.authclient.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class AuthClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthClientApplication.class, args);
	}

}
