package com.example.sb.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//配置后端通信
//Retrofit 初始化
public class ApiClient {
    private static Retrofit retrofit = null;

    public static ApiService getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2/api") // 替换成你的 Spring Boot 地址
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}
