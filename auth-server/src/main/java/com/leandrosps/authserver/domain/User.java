package com.leandrosps.authserver.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
public class User {
    private final String id;
    private final String email;
    private final String username;
    private final String name;
    private final int age;
    private final String password;
    private final List<String> roles;

    public User(String id, String email, String username, String name, int age, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.name = name;
        this.age = age;
        this.password = password;
        this.roles = new ArrayList<>();
    }

    public void addRole(String role) {
        this.roles.add(role);
    }

    static public User create(
            String email,
            String username,
            String name,
            int age,
            String password) {

        return new User(UUID.randomUUID().toString(), email, username, name, age, password);
    }

}
