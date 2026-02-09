package com.dscatalog.aula.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dscatalog.aula.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
