package com.dscatalog.aula.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dscatalog.aula.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
