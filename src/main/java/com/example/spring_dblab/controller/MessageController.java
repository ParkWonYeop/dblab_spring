package com.example.spring_dblab.controller;

import com.example.spring_dblab.service.CustomMessageService;
import com.example.spring_dblab.service.EmailService;
import com.example.spring_dblab.service.KakaoService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {
    KakaoService kakaoService;
    CustomMessageService customMessageService;
    EmailService emailService;

    MessageController(KakaoService kakaoService, CustomMessageService customMessageService, EmailService emailService) {
        this.kakaoService = kakaoService;
        this.customMessageService = customMessageService;
        this.emailService = emailService;
    }

    @GetMapping("/send")
    public String sendMessage(String code) {
        if(kakaoService.getKakaoAuthToken(code)) {
            customMessageService.sendMyMessage();
            return "메시지 전송 성공";
        } else {
            return "토큰발급 실패";
        }
    }

    @GetMapping("/mail")
    public String sendEmail() throws MessagingException, UnsupportedEncodingException {
        this.emailService.sendMail();
        return "메일 전송";
    }
}
