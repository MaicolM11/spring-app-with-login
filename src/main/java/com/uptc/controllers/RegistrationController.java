package com.uptc.controllers;

import com.uptc.models.entities.User;
import com.uptc.services.ConfirmationTokenService;
import com.uptc.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/register")
@RequiredArgsConstructor
public class RegistrationController {
    
    private final UserService userService;
    private final ConfirmationTokenService tokenService;

    @PostMapping
    public String register(@RequestBody User user){
        return userService.registerUser(user);
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        String email = tokenService.confirmToken(token);    // email of user
        userService.enableUser(email);
        return email;
    }

}