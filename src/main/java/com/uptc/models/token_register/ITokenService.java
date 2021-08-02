package com.uptc.models.token_register;

public interface ITokenService {
    void saveConfirmationToken(Token token);
    Integer confirmToken(String token) ;
}
