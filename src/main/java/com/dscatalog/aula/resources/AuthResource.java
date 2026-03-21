package com.dscatalog.aula.resources;

import com.dscatalog.aula.dto.EmailDTO;
import com.dscatalog.aula.dto.NewPasswordDTO;
import com.dscatalog.aula.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private AuthService authService;

	@PostMapping(value = "/recover-token")
	public ResponseEntity<Void> recoveryToken(@Valid @RequestBody EmailDTO body) {
		authService.createRecoveryToken(body);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/new-password")
	public ResponseEntity<Void> saveNewPassword(@Valid @RequestBody NewPasswordDTO body) {
		authService.saveNewPassword(body);
		return ResponseEntity.noContent().build();
	}

}
