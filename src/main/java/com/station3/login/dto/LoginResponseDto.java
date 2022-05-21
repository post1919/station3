package com.station3.login.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
public class LoginResponseDto implements Serializable {
    private static final long serialVersionUID = 5177407244985764350L;

    private String result;
    private String code;
    private String status;
    private String redirect;
    private String redirectParam;
    private Map<String, String> items;
    private CustomUserDetailsDto user;
}
