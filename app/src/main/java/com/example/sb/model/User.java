package com.example.sb.model;

// 获取用户响应

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String account;
    private String pw;//密码
    private String sex;  // "男" or "女"
    private Integer age;
    private String fid;
    private String avatar; // 头像url

}
