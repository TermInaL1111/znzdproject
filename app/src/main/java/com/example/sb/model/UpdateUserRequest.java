package com.example.sb.model;

// 更新用户请求
public class UpdateUserRequest {
    public String account;
    public String sex;
    public int age;
    public String pw; // 可为空
    public UpdateUserRequest(String account, String sex, int age, String pw) {
        this.account = account;
        this.sex = sex;
        this.age = age;
        this.pw = pw;
    }
}