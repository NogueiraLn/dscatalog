package com.dscatalog.aula.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dscatalog.aula.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
