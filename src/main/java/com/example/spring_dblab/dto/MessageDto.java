package com.example.spring_dblab.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MessageDto {
        private String objType;
        private String text;
        private String webUrl;
        private String mobileUrl;
        private String btnTitle;
}
