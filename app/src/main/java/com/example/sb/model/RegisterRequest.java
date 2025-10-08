package com.example.sb.model;

// 注册请求
public class RegisterRequest {
    public String account;
    public String pw;
    public String sex;
    public int age;
    public String fid;

    public RegisterRequest(String account, String pw, String sex, int age, String fid) {
        this.account = account;
        this.pw = pw;
        this.sex = sex;
        this.age = age;
        this.fid = fid;
    }
}