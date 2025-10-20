package com.example.sb.model;

// 通用响应 result

import lombok.Data;

@Data
public class ApiResponse<T> {
        public int code;  // 1 成功，0 失败
        public String msg;
        public T data;
        
    }