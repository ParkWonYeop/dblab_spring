package com.example.spring_dblab.service;

import com.example.spring_dblab.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomMessageService {
    MessageService messageService;

    public boolean sendMyMessage() {
        MessageDto myMsg = new MessageDto();
        myMsg.setBtnTitle("자세히보기");
        myMsg.setMobileUrl("");
        myMsg.setObjType("text");
        myMsg.setWebUrl("");
        myMsg.setText("메시지 테스트입니다.");

        String accessToken = KakaoService.authToken;

        return messageService.sendMessage(accessToken, myMsg);
    }
}
