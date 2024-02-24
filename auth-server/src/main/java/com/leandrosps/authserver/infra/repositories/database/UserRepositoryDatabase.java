package com.leandrosps.authserver.infra.repositories.database;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import com.leandrosps.authserver.domain.User;
import com.leandrosps.authserver.infra.exceptions.CustomError;
import com.leandrosps.authserver.infra.repositories.UserRepository;
import com.leandrosps.authserver.infra.repositories.jpa.UserJPARepository;

@Repository
@Primary
public class UserRepositoryDatabase implements UserRepository {

    @Autowired
    private UserJPARepository userJPARepository;

    public UserRepositoryDatabase(UserJPARepository userJPARepository) {
        this.userJPARepository = userJPARepository;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        var userData = this.userJPARepository.findByEmail(email);

        if (userData == null) {
            return Optional.empty();
        }

        var user = User.create(
                userData.getEmail(),
                userData.getUsername(),
                userData.getName(),
                userData.getAge(),
                userData.getPassword());

        for (int i = 0; i < userData.getRoles().size(); i++) {
            user.addRole(userData.getRoles().get(i).getName());
        }

        return Optional.of(user);
    }

}
