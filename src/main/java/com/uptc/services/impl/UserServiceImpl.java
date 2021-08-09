package com.uptc.services.impl;

import com.uptc.models.entities.User;
import com.uptc.repo.UserRepository;
import com.uptc.services.IUserService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import static com.uptc.utils.Messages.RESOURCE_NOT_FOUND;;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFound = userRepository.findUserByEmail(username)                
                .orElseThrow(() -> new UsernameNotFoundException(
                    String.format(RESOURCE_NOT_FOUND, "User","username",username)));
        return User.build(userFound);   
    }

    @Override
    public boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean enableUser(Integer id) {
        return userRepository.enableUser(id) == 1;
    }

    @Override
    public void saveUser(User u){
        userRepository.save(u);
    }
    
}