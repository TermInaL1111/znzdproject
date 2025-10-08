package com.example.sb.model;

// 登录请求
public class LoginRequest {
    public String account;
    public String pw;
    public LoginRequest(String account, String pw) {
        this.account = account;
        this.pw = pw;
    }
}