package com.uptc.services;

import com.uptc.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.uptc.models.UserRole;
import com.uptc.models.entities.ConfirmationToken;
import com.uptc.models.entities.User;

import static com.uptc.utils.Messages.USER_NOT_FOUND;
import static com.uptc.utils.Messages.EMAIL_IS_NOT_VALID;
import static com.uptc.utils.Validations.isEmail;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ConfirmationTokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public String registerUser(User user) {
        if (!existEmail(user.getEmail()) && isEmail.test(user.getEmail())) {
            user.setUserRole(UserRole.USER);
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            String token = UUID.randomUUID().toString();

            ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(5), user);

            tokenService.saveConfirmationToken(confirmationToken);

            // TODO: send email 
            return token;
        }
        throw new IllegalStateException(String.format(EMAIL_IS_NOT_VALID, user.getEmail()));
    }

    private boolean existEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    public void enableUser(String email) {
        userRepository.enableUser(email);
    }

}
