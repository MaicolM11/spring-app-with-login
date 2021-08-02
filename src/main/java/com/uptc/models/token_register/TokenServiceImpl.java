package com.uptc.models.token_register;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.uptc.exceptions.ResourceNotFoundException;

import static com.uptc.utils.Messages.TOKEN_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements ITokenService{

    private final TokenRepository tokenRepository;

    /***
     * Save a token in redis db
     * @param token string token to save
     */
    @Override
    public void saveConfirmationToken(Token token) {
        tokenRepository.save(token);
    }

    /***
     * Confirm register by token
     * 
     * @param token string token to confirm
     * @return user id
     */
    @Override
    public Integer confirmToken(String token) {
        Token confirmationToken = tokenRepository.findById(token)
                .orElseThrow(() -> new ResourceNotFoundException(TOKEN_NOT_FOUND));
        tokenRepository.deleteById(token);
        return confirmationToken.getUserId();
    }

}