package com.uptc.auth.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginModel {
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

}