package com.example.sb;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.sb.model.GenericResponse;
import com.example.sb.network.ApiClient;
import com.example.sb.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private ImageView ivAvatar;
    private Button btnChangeAvatar, btnSave;
    private EditText etAccount, etAge, etNewPw, etConfirmPw;
    private RadioGroup rgSex;
    private RadioButton rbMale, rbFemale;

    private Uri avatarUri;

    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ivAvatar = view.findViewById(R.id.ivAvatar);
        btnChangeAvatar = view.findViewById(R.id.btnChangeAvatar);
        btnSave = view.findViewById(R.id.btnSave);
        etAccount = view.findViewById(R.id.etAccount);
        etAge = view.findViewById(R.id.etAge);
        etNewPw = view.findViewById(R.id.etNewPw);
        etConfirmPw = view.findViewById(R.id.etConfirmPw);
        rgSex = view.findViewById(R.id.rgSex);
        rbMale = view.findViewById(R.id.rbMale);
        rbFemale = view.findViewById(R.id.rbFemale);

        apiService = ApiClient.getApi();

        btnChangeAvatar.setOnClickListener(v -> pickAvatar());
        btnSave.setOnClickListener(v -> saveProfile());

        loadUser();

        return view;
    }

    private void loadUser() {
        String account = getContext().getSharedPreferences("prefs", 0)
                .getString("currentaccount", null);
        if (account == null) return;

        apiService.getUser(account).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body().code == 1) {
                    UserResponse.Data data = response.body().data;
                    etAccount.setText(data.account);
                    etAge.setText(String.valueOf(data.age));
                    if ("男".equals(data.sex)) rbMale.setChecked(true);
                    else rbFemale.setChecked(true);
                    // TODO: 使用 Glide/Picasso 加载头像
                    // Glide.with(getContext()).load(BASE_URL + data.avatar).into(ivAvatar);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(), "获取用户信息失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pickAvatar() {
        ActivityResultLauncher<String> launcher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        avatarUri = uri;
                        ivAvatar.setImageURI(uri);
                    }
                });
        launcher.launch("image/*");
    }

    private void saveProfile() {
        String account = etAccount.getText().toString();
        String sex = rbMale.isChecked() ? "男" : "女";
        String ageStr = etAge.getText().toString();
        String newPw = etNewPw.getText().toString();
        String confirmPw = etConfirmPw.getText().toString();

        if (!TextUtils.isEmpty(newPw) && !newPw.equals(confirmPw)) {
            Toast.makeText(getContext(), "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        int age;
        try { age = Integer.parseInt(ageStr); } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "年龄必须为数字", Toast.LENGTH_SHORT).show();
            return;
        }

        UpdateUserRequest req = new UpdateUserRequest(account, sex, age, TextUtils.isEmpty(newPw)? null : newPw);

        apiService.updateUser(req).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body().code == 1) {
                    Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                    etNewPw.setText("");
                    etConfirmPw.setText("");
                } else {
                    Toast.makeText(getContext(), response.body().msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });

        // TODO: 如果 avatarUri != null，上传头像接口
    }
}
