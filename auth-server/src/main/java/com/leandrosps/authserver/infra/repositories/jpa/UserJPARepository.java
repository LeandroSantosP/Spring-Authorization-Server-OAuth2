package com.leandrosps.authserver.infra.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leandrosps.authserver.infra.repositories.jpa.JpaObjTableScan.User;

import jakarta.transaction.Transactional;

public interface UserJPARepository extends JpaRepository<User, String> {

    @Transactional
    User findByEmail(String email);
}
