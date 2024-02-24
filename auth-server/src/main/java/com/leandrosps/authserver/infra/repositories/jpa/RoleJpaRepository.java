package com.leandrosps.authserver.infra.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.leandrosps.authserver.infra.repositories.jpa.JpaObjTableScan.Role;

public interface RoleJpaRepository extends JpaRepository<Role, String> {

}
