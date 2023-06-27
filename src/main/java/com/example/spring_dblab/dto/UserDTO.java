package com.example.spring_dblab.dto;

public class UserDTO {
    private String email;
    private String name;
    private String nickname;
    private String rule;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getRule() {
        return rule;
    }
}
