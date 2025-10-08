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

public class LoginFragment extends Fragment {

    private EditText etUsername, etPassword, etConfirmPassword, etAge;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private LinearLayout layoutRegisterFields;
    private Button btnSubmit, btnToggle;
    private TextView tvError;

    private boolean isRegistering = false;

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

        return view;
    }

    private void toggleMode() {
        isRegistering = !isRegistering;
        layoutRegisterFields.setVisibility(isRegistering ? View.VISIBLE : View.GONE);
        btnSubmit.setText(isRegistering ? "注册" : "登录");
        btnToggle.setText(isRegistering ? "已有账号？去登录" : "没有账号？去注册");
        tvError.setText("");
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

            // TODO: 调用注册接口，成功后切换回登录
            Toast.makeText(getContext(), "注册成功，返回登录", Toast.LENGTH_SHORT).show();
            toggleMode();

        } else {
            // TODO: 调用登录接口，成功后跳转到 ManagerFragment / ProfileFragment
            Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_profileFragment);
        }
    }
}
