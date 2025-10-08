package com.example.sb.model;

// 获取用户响应
public class UserResponse {
    public int code;
    public String msg;
    public Data data;

    public class Data {
        public String account;
        public String sex;
        public int age;
        public String avatar;
    }
}
