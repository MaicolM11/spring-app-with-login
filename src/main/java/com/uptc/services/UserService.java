package com.uptc.services;

import com.uptc.repository.UserRepository;
import com.uptc.utils.Validations;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.uptc.exceptions.BadRequestException;
import com.uptc.models.UserRole;
import com.uptc.models.entities.Token;
import com.uptc.models.entities.User;

import static com.uptc.utils.Messages.USER_NOT_FOUND;
import static com.uptc.utils.Messages.EMAIL_IS_NOT_VALID;
import static com.uptc.utils.Validations.isEmail;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final TokenService tokenService;
    private final EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public String registerUser(User user) {
        if (existEmail(user.getEmail()) || !isEmail.test(user.getEmail()))
            throw new BadRequestException(String.format(EMAIL_IS_NOT_VALID, user.getEmail()));

        user.setUserRole(UserRole.USER);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        Token confirmationToken = new Token(token, user.getId());
        tokenService.saveConfirmationToken(confirmationToken);

        String link = "http://localhost:3000/register/confirm?token=" + token;
        emailService.send(user.getEmail(), Validations.buildEmail(user.getFirstName(), link));
        return token;
    }

    private boolean existEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    public void enableUser(String token) {
        Long id = tokenService.confirmToken(token);
        userRepository.enableUser(id);
    }

}
