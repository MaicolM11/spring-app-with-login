package com.uptc.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import static com.uptc.utils.Messages.USER_NOT_FOUND;

import com.uptc.models.entities.User;
import com.uptc.repo.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    /***
     * for security config
     */
    @Override           
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public boolean existEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    public void enableUser(Integer id) {
        userRepository.enableUser(id);
    }

    public void saveUser(User u){
        userRepository.save(u);
    }

}