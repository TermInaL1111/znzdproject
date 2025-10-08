package com.example.sb.model;

// 通用响应 result
public class GenericResponse {
    public int code; // 1 成功，0 失败
    public String msg;
    public Data data;

    public class Data {
        public String token; // 登录返回 token
    }
}