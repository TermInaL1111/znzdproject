package com.example.sb.network;


import com.example.sb.model.ApiResponse;
import com.example.sb.model.LoginRequest;
import com.example.sb.model.LoginResponse;
import com.example.sb.model.RegisterRequest;
import com.example.sb.model.UpdateUserRequest;
import com.example.sb.model.User;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


//Retrofit 接口
public interface ApiService {
    @POST("login")
    Call<ApiResponse<LoginResponse>> login(@Body LoginRequest request);

    @POST("register")
    Call<ApiResponse<Void>> register(@Body RegisterRequest request);
    // 获取用户信息
    @GET("getuser")
    Call<ApiResponse<User>> getUser(@Query("account") String account);

    // 更新用户信息
    @POST("updateuser")
    Call<ApiResponse<Void>> updateUser(@Body UpdateUserRequest request);

    // 上传头像
    @Multipart
    @POST("uploadAvatar")
    Call<ApiResponse<String>> uploadAvatar(
            @Part MultipartBody.Part file,
            @Part("account") String account
    );

}
