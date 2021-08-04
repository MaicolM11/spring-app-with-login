package com.uptc.api;

import com.uptc.jwt.JWTUtils;
import com.uptc.models.LoginModel;
import com.uptc.models.RegisterModel;
import com.uptc.services.IAuthService;
import com.uptc.utils.Url;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping(Url.AUTH_RESOURCE)
@AllArgsConstructor
public class AuthResource {

    private final IAuthService authService;

    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;

    @PostMapping("/signup")
    public String register(@Valid @RequestBody RegisterModel newUser) {
        return authService.signUp(newUser);
    }

    @GetMapping(path = "/confirm")
    public void confirm(@RequestParam("token") String token) {
        authService.confirmEmail(token);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginModel newUser) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(newUser.getUsername(), newUser.getPassword()));
        User user = (User) authentication.getPrincipal();
        List<String> user_roles = authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList());
        String access_token = jwtUtils.generateJwtToken(user.getUsername(),  user_roles);
        return access_token;
    }

}