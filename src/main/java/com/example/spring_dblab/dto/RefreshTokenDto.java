package com.example.spring_dblab.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RefreshTokenDto {
    private String refreshToken;
}
