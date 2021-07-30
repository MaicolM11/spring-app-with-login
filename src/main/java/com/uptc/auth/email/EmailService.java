package com.uptc.auth.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.uptc.exceptions.InternalServerError;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.uptc.utils.Messages.SEND_EMAIL_ERROR;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {
     
    private final JavaMailSender javaMailSender;
    
    @Async
    public void send(String to, String email){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setTo(to);
            helper.setText(email, true);
            helper.setSubject("Confirm your email");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(SEND_EMAIL_ERROR, e);
            throw new InternalServerError(SEND_EMAIL_ERROR);
        }
    }

}