package com.example.sb;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.sb.model.ApiResponse;

import com.example.sb.model.LoginRequest;
import com.example.sb.model.LoginResponse;
import com.example.sb.model.RegisterRequest;
import com.example.sb.model.User;
import com.example.sb.network.ApiClient;
import com.example.sb.network.ApiService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    private EditText etUsername, etPassword, etConfirmPassword, etAge;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private LinearLayout layoutRegisterFields;
    private Button btnSubmit, btnToggle;
    private TextView tvError;

    private boolean isRegistering = false;

    private ApiService api;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        etAge = view.findViewById(R.id.etAge);

        rgGender = view.findViewById(R.id.rgGender);
        rbMale = view.findViewById(R.id.rbMale);
        rbFemale = view.findViewById(R.id.rbFemale);

        layoutRegisterFields = view.findViewById(R.id.layoutRegisterFields);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnToggle = view.findViewById(R.id.btnToggle);
        tvError = view.findViewById(R.id.tvError);

        btnToggle.setOnClickListener(v -> toggleMode());
        btnSubmit.setOnClickListener(v -> submit());

        api = ApiClient.getApi();

        return view;
    }

    private void toggleMode() {
        isRegistering = !isRegistering;
        layoutRegisterFields.setVisibility(isRegistering ? View.VISIBLE : View.GONE);
        btnSubmit.setText(isRegistering ? "注册" : "登录");
        btnToggle.setText(isRegistering ? "已有账号？去登录" : "没有账号？去注册");
        tvError.setText("");
        etPassword.setText("");
        etConfirmPassword.setText("");
        etAge.setText("");
        rgGender.clearCheck();
    }

    private void submit() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        tvError.setText("");

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            tvError.setText("用户名和密码不能为空");
            return;
        }

        if (isRegistering) {
            String confirm = etConfirmPassword.getText().toString().trim();
            String ageStr = etAge.getText().toString().trim();
            int selectedGenderId = rgGender.getCheckedRadioButtonId();

            if (TextUtils.isEmpty(confirm) || TextUtils.isEmpty(ageStr) || selectedGenderId == -1) {
                tvError.setText("请填写完整注册信息");
                return;
            }

            if (!password.equals(confirm)) {
                tvError.setText("两次密码不一致");
                return;
            }

            int age;
            try { age = Integer.parseInt(ageStr); } catch (NumberFormatException e) {
                tvError.setText("年龄必须是数字");
                return;
            }

            String gender = (selectedGenderId == R.id.rbMale) ? "男" : "女";

            // Retrofit 注册请求
            RegisterRequest req = new RegisterRequest(username, password, gender, age, username,null);
            api.register(req).enqueue(new Callback<ApiResponse<Void>>() {
                @Override
                public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ApiResponse<Void> res = response.body();
                        if (res.getCode() == 1) {
                            Toast.makeText(getContext(), "注册成功，请登录", Toast.LENGTH_SHORT).show();
                            toggleMode();
                        } else {
                            tvError.setText(res.getMsg());
                        }
                    } else {
                        tvError.setText("注册失败，服务器返回错误");
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                    tvError.setText("请求失败：" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            // Retrofit 登录请求
            tvError.setText("666");
            LoginRequest req = new LoginRequest(username, password);
            api.login(req).enqueue(new Callback<ApiResponse<LoginResponse>>() {
                @Override
                public void onResponse(Call<ApiResponse<LoginResponse>> call, Response<ApiResponse<LoginResponse>> response) {
                    ApiResponse<LoginResponse> res = response.body();
                    tvError.setText("666请求失败：" + res);
                    if(res != null && res.getCode() == 1){
                        // 保存 token
                        getActivity().getSharedPreferences("app", 0)
                                .edit()
                                .putString("token", res.getData().getToken())
                                .putString("account", username)
                                .apply();

                        // 跳转 ProfileFragment / MainFragment
//                        Navigation.findNavController(getView())
//                                .navigate(R.id.action_loginFragment_to_profileFragment);

                    } else {
                        tvError.setText(res != null ? res.msg : "登录失败");
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<LoginResponse>> call, Throwable t) {
                    tvError.setText("请求失败：" + t.getMessage());
                    t.printStackTrace();
                }
            });
        }
    }
}

//你现在遇到的错误是 Android 9+ 默认不允许明文 HTTP 通信：
//
//CLEARTEXT communication to 10.0.2.2 not permitted by network security policy
//
//
//也就是说，你用的 http://10.0.2.2:8080/ 是明文 HTTP，被系统阻止了。
//解决方案 2：使用 HTTPS（更安全，生产推荐）
//
//给 Spring Boot 配置 SSL，使用 https://10.0.2.2:8443/。
//
//需要生成自签名证书，Android Emulator 信任即可。
//
//开发阶段通常用方案 1，生产环境必须用 HTTPS。