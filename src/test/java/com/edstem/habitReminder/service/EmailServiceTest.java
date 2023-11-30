package com.edstem.habitReminder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class EmailServiceTest {
    @MockBean private JavaMailSender javaMailSender;

    @Autowired private EmailService emailService;

    @Test
    public void testSendEmail_Success() {
        String userEmail = "test@example.com";
        String emailSubject = "Test Subject";
        String emailBody = "Test Body";

        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        emailService.sendEmail(userEmail, emailSubject, emailBody);

        ArgumentCaptor<SimpleMailMessage> argumentCaptor =
                ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender, times(1)).send(argumentCaptor.capture());

        SimpleMailMessage capturedMessage = argumentCaptor.getValue();

        assertEquals(userEmail, capturedMessage.getTo()[0]);
        assertEquals(emailSubject, capturedMessage.getSubject());
        assertEquals(emailBody, capturedMessage.getText());
    }

    @Test
    public void testSendEmail_Failure() {
        String userEmail = "test@example.com";
        String emailSubject = "Test Subject";
        String emailBody = "Test Body";

        doThrow(new RuntimeException("Failed to send email"))
                .when(javaMailSender)
                .send(any(SimpleMailMessage.class));

        assertThrows(
                RuntimeException.class,
                () -> emailService.sendEmail(userEmail, emailSubject, emailBody));
    }
}
