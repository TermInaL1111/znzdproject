package com.example.sb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//登陆
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String account;
    private String fid;
    private String token;
}
