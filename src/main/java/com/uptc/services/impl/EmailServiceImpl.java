package com.uptc.services.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.uptc.exceptions.InternalServerError;
import com.uptc.services.IEmailService;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.uptc.utils.Messages.SEND_EMAIL_SUBJECT;
import static com.uptc.utils.Messages.SEND_EMAIL_ERROR;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableAsync
public class EmailServiceImpl implements IEmailService {
     
    private static final String FORMAT_EMAIL = "utf-8";

    private final JavaMailSender javaMailSender;
    
    @Async
    @Override
    public void send(String to, String email){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, FORMAT_EMAIL);
        try {
            helper.setTo(to);
            helper.setText(email, true);
            helper.setSubject(SEND_EMAIL_SUBJECT);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(SEND_EMAIL_ERROR, e);
            throw new InternalServerError(SEND_EMAIL_ERROR);
        }
    }

}