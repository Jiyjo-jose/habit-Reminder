package com.edstem.habitReminder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public void sendEmail(String UserEmail, String emailSubject, String emailBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(UserEmail);
        message.setSubject(emailSubject);
        message.setText(emailBody);
        emailSender.send(message);

        try {
            emailSender.send(message);

        } catch (MailException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
