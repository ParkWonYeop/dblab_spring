package com.example.spring_dblab.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String ADMIN_ADDRESS;

    @Async
    public void sendMail() throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO,"pwy9679@gnu.ac.kr");
        message.setSubject("테스트");
        String text = "테스트메세지입니다";
        message.setText(text,"utf-8");
        message.setFrom(new InternetAddress(ADMIN_ADDRESS,"이름"));
        javaMailSender.send(message);
    }
}
