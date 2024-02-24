package com.leandrosps.authserver.infra.repositories;

import java.util.Optional;

import com.leandrosps.authserver.domain.User;

/**
 * UserRepository
 */
public interface UserRepository {
    Optional<User> getByEmail(String email);
}