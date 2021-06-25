package com.uptc.services;

import com.uptc.models.entities.ConfirmationToken;
import com.uptc.repository.ConfirmationTokenRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import static com.uptc.utils.Messages.TOKEN_NOT_FOUND;

import java.time.LocalDateTime;

import static com.uptc.utils.Messages.EMAIL_ALREADY_CONFIRMED;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository tokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        tokenRepository.save(token);
    }

    /***
     * Confirm the token
     * @param token token to confirm
     * @return user's gmail
     */
    public String confirmToken(String token) {

        ConfirmationToken confirmationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException(TOKEN_NOT_FOUND));

        if (confirmationToken.getConfirmedAt() != null)
            throw new IllegalStateException(EMAIL_ALREADY_CONFIRMED);
        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new IllegalStateException("Token expired");
        tokenRepository.updateConfirmedAt(token, LocalDateTime.now());

        return confirmationToken.getUser().getEmail();
        
    }

}
