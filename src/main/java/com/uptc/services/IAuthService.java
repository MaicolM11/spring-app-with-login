package com.uptc.services;

import com.uptc.models.RegisterModel;

/**
 * Best practice, if you want change of auth provider 
 */
public interface IAuthService {

    void confirmEmail(String token);
    String signUp(RegisterModel user);
    String signIn(RegisterModel user);

}