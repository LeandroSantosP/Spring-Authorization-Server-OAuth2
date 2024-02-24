package com.leandrosps.authserver.infra.repositories;

import com.leandrosps.authserver.domain.User;

/**
 * UserRepository
 */
public interface UserRepository {
    User getByEmail(String email);
}