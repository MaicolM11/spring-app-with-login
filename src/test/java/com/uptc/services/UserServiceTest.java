package com.uptc.services;

import com.uptc.models.EUserRole;
import com.uptc.models.entities.Role;
import com.uptc.models.entities.User;
import com.uptc.repo.UserRepository;
import com.uptc.services.impl.UserServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class) // runwith junit version < 5
public class UserServiceTest {

    @Mock
    private UserRepository repo;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void existEmailTest() {
        when(repo.existsByEmail("pepe@gmail.com")).thenReturn(true);
        boolean existEmail = userService.existEmail("pepe@gmail.com");
        assertTrue(existEmail);
    }

    @Test
    void existAnyEmailTest() {
        when(repo.existsByEmail(anyString())).thenReturn(false);
        boolean existEmail = userService.existEmail("testemail");
        assertFalse(existEmail);
    }

    @Test
    void enableUserTest() {
        when(repo.enableUser(10)).thenReturn(1).thenReturn(2).thenReturn(1).thenReturn(0);
        boolean enableUser1 = userService.enableUser(10);
        boolean enableUser2 = userService.enableUser(10);
        boolean enableUser3 = userService.enableUser(10);
        boolean enableUser4 = userService.enableUser(10);
        boolean enableUser5 = userService.enableUser(10);
        assertTrue(enableUser1);
        assertFalse(enableUser2);
        assertTrue(enableUser3);
        assertFalse(enableUser4);
        assertFalse(enableUser5);
    }

    @Test
    void loadUserByUsernameFailTest() {
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("pepe@gmail.com"));
    }

    @Test
    void loadUserByUsernameTest() {
        User user = new User("Pepe perez", "pepe@gmail.com", "123");
        when(repo.findUserByEmail("pepe@gmail.com")).thenReturn(Optional.of(user));
        UserDetails findUser = userService.loadUserByUsername("pepe@gmail.com");  
        assertEquals(user.getEmail(), findUser.getUsername());
        assertEquals(user.getPassword(), findUser.getPassword());
        assertFalse(findUser.isEnabled()); // account is disable
    }

    @Test
    void loadUserByUsernameTest_withCredentials() {
        User user = new User("Pepe perez", "pepe@gmail.com", "123");
        user.setRoles(Collections.singleton(new Role(EUserRole.USER)));
        when(repo.findUserByEmail("pepe@gmail.com")).thenReturn(Optional.of(user));
        UserDetails findUser = userService.loadUserByUsername("pepe@gmail.com");  
        assertEquals(1, findUser.getAuthorities().size());
        assertEquals("[ROLE_USER]", findUser.getAuthorities().toString());
    }

}