package com.dscatalog.aula.repositories;

import com.dscatalog.aula.entities.PasswordRecover;
import com.dscatalog.aula.entities.User;
import com.dscatalog.aula.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordRecoverRepository extends JpaRepository<PasswordRecover, Long> {

}
