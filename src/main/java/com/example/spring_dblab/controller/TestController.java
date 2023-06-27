package com.example.spring_dblab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @GetMapping("/test")
    public ResponseEntity<Map<String,String>> home() {
        Map<String,String> model = new HashMap<>();
        model.put("title", "안녕하세요");
        model.put("message", "테스트입니다.");
        return ResponseEntity.ok(model);
    }

    @PostMapping("/test")
    public long test(@RequestBody long number) {
        return number+1;
    }
}
