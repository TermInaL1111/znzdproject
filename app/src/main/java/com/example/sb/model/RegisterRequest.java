package com.example.sb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 注册请求
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    public String account;
    public String pw;
    public String sex;
    public int age;
    public String fid;
    public String avatar;
//    public RegisterRequest(String account, String pw, String sex, int age, String fid) {
//        this.account = account;
//        this.pw = pw;
//        this.sex = sex;
//        this.age = age;
//        this.fid = fid;
//
//    }
}