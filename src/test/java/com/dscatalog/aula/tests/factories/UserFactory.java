package com.dscatalog.aula.tests.factories;

import com.dscatalog.aula.entities.Role;
import com.dscatalog.aula.entities.User;

public class UserFactory {

    public static User createUser() {
        User user = new User(2L, "Maria", "Green", "maria@gmail.com", "$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG");
        user.addRole(new Role(1L, "ROLE_OPERATOR"));
        return user;
    }
    public static User createAdminUser() {
        User user = new User(1L, "Alex", "Brown", "alex@gmail.com", "$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG");
        user.addRole(new Role(2L, "ROLE_ADMIN"));
        return user;
    }
    public static User createCustomClientUser(Long id, String username) {
        User user = new User(id, "Maria", "Green", username, "$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG");
        user.addRole(new Role(2L, "ROLE_OPERATOR"));
        return user;
    }
    public static User createCustomAdminUser(Long id, String username) {
        User user = new User(id, "Alex", "Brown", username, "$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG");
        user.addRole(new Role(2L, "ROLE_ADMIN"));
        return user;
    }

}
