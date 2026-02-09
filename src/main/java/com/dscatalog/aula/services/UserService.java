package com.dscatalog.aula.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dscatalog.aula.dto.RoleDTO;
import com.dscatalog.aula.dto.UserDTO;
import com.dscatalog.aula.dto.UserInsertDTO;
import com.dscatalog.aula.entities.Role;
import com.dscatalog.aula.entities.User;
import com.dscatalog.aula.repositories.RoleRepository;
import com.dscatalog.aula.repositories.UserRepository;
import com.dscatalog.aula.services.exceptions.DatabaseException;
import com.dscatalog.aula.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	@Autowired 
	private UserRepository repository;
	
	@Autowired 
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Transactional(readOnly = true)
	public List<UserDTO> findAll() {
		List<User> list = repository.findAll();
		return list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable) {
		Page<User> list = repository.findAll(pageable);
		return list.map(x -> new UserDTO(x));
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> entity = repository.findById(id);
		User user = entity.orElseThrow(() -> new ResourceNotFoundException("Entity not Found"));
		return new UserDTO(user);
	}

	@Transactional
	public UserDTO insert(UserInsertDTO dto) {
		User entity = new User();
		copyDtoToEntity(dto, entity);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity = repository.save(entity);
		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
		try {
			User entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new UserDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found - " + id);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
	    	catch (DataIntegrityViolationException e) {
	        	throw new DatabaseException("Entegrity Violation");
	   	}
	}
	
	private void copyDtoToEntity(UserDTO dto, User entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());
		entity.getRoles().clear();
		for(RoleDTO catDTO : dto.getRoles()) {
			Role roleEntity = roleRepository.getReferenceById(catDTO.getId());
			entity.getRoles().add(roleEntity);
		}
	}
}
