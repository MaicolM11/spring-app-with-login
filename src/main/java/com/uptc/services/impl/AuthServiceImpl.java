package com.uptc.services.impl;

import java.util.Collections;
import java.util.UUID;

import com.uptc.exceptions.BadRequestException;
import com.uptc.exceptions.ResourceNotFoundException;
import com.uptc.models.EUserRole;
import com.uptc.models.RegisterModel;
import com.uptc.models.entities.Role;
import com.uptc.models.entities.User;
import com.uptc.models.token_register.ITokenService;
import com.uptc.models.token_register.Token;
import com.uptc.repo.RoleRepository;
import com.uptc.services.IAuthService;
import com.uptc.services.IEmailService;
import com.uptc.services.IUserService;
import com.uptc.utils.Url;
import com.uptc.utils.Validations;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import static com.uptc.utils.Messages.EMAIL_IS_NOT_VALID;
import static com.uptc.utils.Messages.RESOURCE_NOT_FOUND;;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IUserService userService;
    private final ITokenService tokenService; 
    private final IEmailService emailService;
    private final PasswordEncoder encoder; 
    private final RoleRepository roleRepository;

    @Override
    public void confirmEmail(String token) {
        Integer id = tokenService.confirmToken(token);
        userService.enableUser(id);
    }

    @Override
    public String signUp(RegisterModel newUser) {
        if (userService.existEmail(newUser.getEmail()))
            throw new BadRequestException(String.format(EMAIL_IS_NOT_VALID, newUser.getEmail()));

        User user = new User(newUser.getFullname(), newUser.getEmail(), encoder.encode(newUser.getPassword()));
        Role userRole = roleRepository.findByRole(EUserRole.USER)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND, "Role", "value", "USER")));
        user.setRoles(Collections.singletonList(userRole));
        userService.saveUser(user);
        return sendTokenConfirmation(user);
    }

    @Override
    public String signIn(RegisterModel user) {
        return "";
    }

    /***
     * Create a token register and save it
     * @param user User to create the token
     * @return the token
     */
    public String sendTokenConfirmation(User user){
        String token = UUID.randomUUID().toString(); 
        Token confirmationToken = new Token(token, user.getId());
        tokenService.saveConfirmationToken(confirmationToken); 
        String link = Url.COMFIRM_TOKEN + token;
        emailService.send(user.getEmail(),
                Validations.buildEmail(user.getFullname(), link));
        return token;
    }

}
