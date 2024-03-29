package com.leandrosps.authserver.infra.repositories.jpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.leandrosps.authserver.infra.repositories.jpa.JpaObjTableScan.Role;
import com.leandrosps.authserver.infra.repositories.jpa.JpaObjTableScan.User;

@Configuration
public class JpaSeed {

    @Autowired
    RoleJpaRepository roleJPARepository;

    @Autowired
    UserJPARepository userJPARepository;

    @Bean
    public Object createDefaultRoles(PasswordEncoder passwordEncoder) {
        var roleUSER = roleJPARepository
                .save(new Role(UUID.randomUUID().toString(), "USER", Collections.emptyList()));
        var roleADMIN = roleJPARepository
                .save(new Role(UUID.randomUUID().toString(), "ADMIN", Collections.emptyList()));

        userJPARepository.save(new User(
                UUID.randomUUID().toString(),
                "john",
                "john.admin@gmail.com",
                "John_Doe", 19,
                passwordEncoder.encode("senha123"),
                new ArrayList<>(Arrays.asList(roleADMIN))));

        userJPARepository.save(new User(
                UUID.randomUUID().toString(),
                "john",
                "john.user@gmail.com",
                "John_Doe", 19,
                passwordEncoder.encode("senha123"),
                new ArrayList<>(Arrays.asList(roleUSER))));
        return new Object();
    }

}