package com.uptc.services;

import com.uptc.models.entities.Token;
import com.uptc.repository.TokenRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import static com.uptc.utils.Messages.TOKEN_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    /***
     * Save a token in redis db
     * @param token
     */
    public void saveConfirmationToken(Token token) {
        tokenRepository.save(token);
    }

    /***
     * Confirm register by token 
     * @param token string token to confirm
     * @return user id
     */
    public Long confirmToken(String token) {
        Token confirmationToken = tokenRepository.findById(token)
                        .orElseThrow(()-> new IllegalStateException(TOKEN_NOT_FOUND));
        tokenRepository.deleteById(token);
        return confirmationToken.getUserId();
    }

}