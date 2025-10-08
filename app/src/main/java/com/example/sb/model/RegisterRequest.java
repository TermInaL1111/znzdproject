package com.example.sb.model;

// 注册请求
public class RegisterRequest {
    public String account, pw, sex;
    public int age;
    public RegisterRequest(String account, String pw, String sex, int age) {
        this.account = account;
        this.pw = pw;
        this.sex = sex;
        this.age = age;
    }
}