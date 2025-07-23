package com.example.anhkiet3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.anhkiet3.LoginResponse;
import com.example.anhkiet3.UserApi;
import static android.content.Context.MODE_PRIVATE;
import com.example.anhkiet3.R;
import com.example.anhkiet3.LoginRequest;
import com.example.anhkiet3.MainActivity;
import com.example.anhkiet3.RegisterActivity;
import com.example.anhkiet3.ForgotPasswordActivity;
import android.content.SharedPreferences;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);
        TextView tvRegister = findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            // Kiểm tra đăng nhập mock bằng email
            SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
            String savedEmail = prefs.getString("mock_email", null);
            String savedPassword = prefs.getString("mock_password", null);
            if (email.equals(savedEmail) && password.equals(savedPassword)) {
                Toast.makeText(this, "Đăng nhập thành công (mock)", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
            // Nếu không đúng mock, gọi API thật như cũ
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://fakestoreapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UserApi userApi = retrofit.create(UserApi.class);
            String username = email.equals("john@gmail.com") ? "johnd" : email;
            LoginRequest request = new LoginRequest(username, password);
            userApi.login(request).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getToken() != null) {
                        // Lưu token vào SharedPreferences
                        String token = response.body().getToken();
                        getSharedPreferences("MyApp", MODE_PRIVATE)
                                .edit()
                                .putString("token", token)
                                .apply();
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }
}
