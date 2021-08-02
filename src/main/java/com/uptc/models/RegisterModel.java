package com.uptc.models;

import lombok.Getter;

import java.util.Set;

import javax.validation.constraints.*;

@Getter
public class RegisterModel {
    
    @NotBlank
    @Size(min = 3, max = 20)
    private String fullname;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

}