package com.dscatalog.aula.services;

import com.dscatalog.aula.dto.UserDTO;
import com.dscatalog.aula.entities.User;
import com.dscatalog.aula.projections.UserDetailsProjection;
import com.dscatalog.aula.repositories.UserRepository;
import com.dscatalog.aula.tests.factories.UserDetailsFactory;
import com.dscatalog.aula.tests.factories.UserFactory;
import com.dscatalog.aula.utils.CustomUserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private CustomUserUtil userUtil;

    private User user;

    private List<UserDetailsProjection> userDetails;

    private String existingUsername;
    private String nonExistingUsername;

    @BeforeEach
    void setUp() throws Exception {
        existingUsername = "maria@gmail.com";
        nonExistingUsername = "teste@gmail.com";

        user = UserFactory.createCustomClientUser(1L, existingUsername);
        userDetails = UserDetailsFactory.createCustomAdminUser(existingUsername);


        when(repository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDetails);
        when(repository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(new ArrayList<>());

        when(repository.findByEmail(existingUsername)).thenReturn(user);
        when(repository.findByEmail(nonExistingUsername)).thenThrow(UsernameNotFoundException.class);

    }

    @Test
    public void loadUserByUserNameShouldReturnUserDetailsWhenUserExists(){
        UserDetails userDetail = service.loadUserByUsername(existingUsername);

        Assertions.assertNotNull(userDetail);
        Assertions.assertEquals(existingUsername, userDetail.getUsername());
    }

    @Test
    public void loadUserByUserNameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists(){
        Assertions.assertThrows(UsernameNotFoundException.class,() -> {
            service.loadUserByUsername(nonExistingUsername);
        });

    }

    @Test
    public void authenticatedShouldReturnUserWhenUserExists(){
        when(userUtil.getLoggedUsername()).thenReturn(existingUsername);
        User result = service.authenticated();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingUsername, result.getUsername());
    }

    @Test
    public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists(){
        when(userUtil.getLoggedUsername()).thenReturn(nonExistingUsername);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            User result = service.authenticated();
        });

    }

    @Test
    public void findMeShouldReturnUserDTOWhenUserAuthenticated(){
        UserService userService = spy(service);
        doReturn(user).when(userService).authenticated();

        UserDTO userDTO = userService.findMe();

        Assertions.assertNotNull(userDTO);
        Assertions.assertEquals(existingUsername, user.getUsername());

    }

    @Test
    public void findMeShouldThrowUsernameNotFoundExceptionWhenUserNotAuthenticated(){
        UserService userService = spy(service);
        doThrow(UsernameNotFoundException.class).when(userService).authenticated();

        Assertions.assertThrows(UsernameNotFoundException.class, userService::findMe);

    }


}
