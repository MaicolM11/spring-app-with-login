package com.uptc.api;

import com.uptc.models.LoginModel;
import com.uptc.models.RegisterModel;
import com.uptc.services.IAuthService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/signup")
    public String register(@Valid @RequestBody RegisterModel newUser) {
        return authService.signUp(newUser);
    }

    @GetMapping(path = "/confirm")
    public void confirm(@RequestParam("token") String token) {
        authService.confirmEmail(token);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginModel data) {
        return "";
    }

}