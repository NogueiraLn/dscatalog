package com.dscatalog.aula.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dscatalog.aula.dto.CategoryDTO;
import com.dscatalog.aula.entities.Category;
import com.dscatalog.aula.repositories.CategoryRepository;
import com.dscatalog.aula.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired 
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll();
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> entity = repository.findById(id);
		Category cat = entity.orElseThrow(() -> new EntityNotFoundException("Entity not Found"));
		return new CategoryDTO(cat);
	}
	
}
