package com.uptc.services;

import com.uptc.models.entities.User;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    
    boolean existEmail(String email);
    boolean enableUser(Integer id);
    void saveUser(User u);

}
