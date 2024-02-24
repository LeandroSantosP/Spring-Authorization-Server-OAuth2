
package com.leandrosps.authserver.infra.repositories.inMemory;

import com.leandrosps.authserver.domain.User;
import com.leandrosps.authserver.infra.repositories.UserRepository;

import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Primary
public class UserRepositoryInMemory implements UserRepository {

    private List<User> users = new ArrayList<>();

    public UserRepositoryInMemory() {
        User userNormal = User.create("john.doe@gmail.com", "John_Doe", "John", 29,
                new BCryptPasswordEncoder(11).encode("senha123"));
        userNormal.addRole("USER");

        User userAdmin = User.create("john.admin@gmail.com", "John_Doe", "John", 29,
                new BCryptPasswordEncoder(11).encode("senha123"));
        userAdmin.addRole("ADMIN");
        users.add(userNormal);
        users.add(userAdmin);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

}
