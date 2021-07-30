package com.uptc.auth;

import com.uptc.auth.email.EmailService;
import com.uptc.auth.model.LoginModel;
import com.uptc.auth.model.RegisterModel;
import com.uptc.auth.register.token.Token;
import com.uptc.auth.register.token.TokenService;
import com.uptc.exceptions.BadRequestException;
import com.uptc.exceptions.ResourceNotFoundException;
import com.uptc.models.EUserRole;
import com.uptc.models.entities.Role;
import com.uptc.models.entities.User;
import com.uptc.repo.RoleRepository;
import com.uptc.services.UserService;
import com.uptc.utils.Validations;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import static com.uptc.utils.Messages.EMAIL_IS_NOT_VALID;

import java.util.Collections;
import java.util.UUID;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    @PostMapping("/signup")
    public String register(@Valid @RequestBody RegisterModel newUser) {
        if (userService.existEmail(newUser.getEmail()))
            throw new BadRequestException(String.format(EMAIL_IS_NOT_VALID, newUser.getEmail()));

        User user = new User(newUser.getFullname(), newUser.getEmail(), encoder.encode(newUser.getPassword()));
        
        Role userRole = roleRepository.findByRole(EUserRole.USER)
					.orElseThrow(() -> new ResourceNotFoundException("User role is not found."));
		user.setRoles(Collections.singletonList(userRole));
        userService.saveUser(user);
        return sendTokenConfirmation(user);
    }

    @GetMapping(path = "/confirm")
    public void confirm(@RequestParam("token") String token) {
        Integer id = tokenService.confirmToken(token);
        userService.enableUser(id);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginModel data) {
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
        String link = "http://localhost:3000/register/confirm?token=" + token;
        emailService.send(user.getEmail(),
                Validations.buildEmail(user.getFullname(), link));
        return token;
    }

}