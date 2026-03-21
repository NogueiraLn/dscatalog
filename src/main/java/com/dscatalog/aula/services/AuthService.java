package com.dscatalog.aula.services;

import com.dscatalog.aula.dto.EmailDTO;
import com.dscatalog.aula.dto.NewPasswordDTO;
import com.dscatalog.aula.entities.PasswordRecover;
import com.dscatalog.aula.entities.User;
import com.dscatalog.aula.repositories.PasswordRecoverRepository;
import com.dscatalog.aula.repositories.UserRepository;
import com.dscatalog.aula.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

	@Value(value = "${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String recoverUri;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRecoverRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void createRecoveryToken(@Valid EmailDTO body) {
        if(userRepository.findByEmail(body.getEmail()) == null){
            throw new ResourceNotFoundException("Email não encontrado");
        }

        String token = UUID.randomUUID().toString();

        PasswordRecover passRecover = new PasswordRecover();
        passRecover.setEmail(body.getEmail());
        passRecover.setToken(token);

        passRecover.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));

        repository.save(passRecover);

        String text = "Acesse o link para definir nova senha\n\n" +
                 recoverUri + token + ". Validade de " + tokenMinutes + " minutos";

        emailService.sendEmail(body.getEmail(), "Recuperação de senha", text);

    }

    @Transactional
    public void saveNewPassword(@Valid NewPasswordDTO body) {
        List<PasswordRecover> result = repository.searchValidTokens(body.getToken(), Instant.now());
        if (result.isEmpty()){
            throw new ResourceNotFoundException("Token Invalido");
        }

        User user = userRepository.findByEmail(result.get(0).getEmail());
        user.setPassword(passwordEncoder.encode(body.getPassword()));

        userRepository.save(user);
    }
}
