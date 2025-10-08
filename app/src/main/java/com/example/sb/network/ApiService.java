package com.example.sb.network;

import com.example.sb.model.GenericResponse;
import com.example.sb.model.LoginRequest;
import com.example.sb.model.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login")
    Call<GenericResponse> login(@Body LoginRequest request);

    @POST("register")
    Call<GenericResponse> register(@Body RegisterRequest request);
}
