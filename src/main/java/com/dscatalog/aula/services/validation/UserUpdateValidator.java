package com.dscatalog.aula.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.dscatalog.aula.dto.UserUpdateDTO;
import com.dscatalog.aula.entities.User;
import com.dscatalog.aula.repositories.UserRepository;
import com.dscatalog.aula.resources.exceptions.FieldMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public void initialize(UserUpdateValid ann) {
	}

	@Override
	public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		var uriVar = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		long userId = Long.parseLong(uriVar.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		User user = repository.findByEmail(dto.getEmail());
		if(user.getEmail() != null && userId != user.getId()) {
			list.add(new FieldMessage("email", "Email ja existe"));
		}
			
			
			
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getFieldMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
