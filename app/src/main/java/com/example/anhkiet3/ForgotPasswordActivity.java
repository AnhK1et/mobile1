package com.example.anhkiet3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        EditText etPhone = findViewById(R.id.etPhone);
        Button btnReset = findViewById(R.id.btnReset);
        Button btnBackToLogin = findViewById(R.id.btnBackToLogin);

        btnReset.setOnClickListener(v -> {
            String phone = etPhone.getText().toString();
            if (phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: Xử lý gửi yêu cầu lấy lại mật khẩu
                Toast.makeText(this, "Đã gửi yêu cầu lấy lại mật khẩu (demo)", Toast.LENGTH_SHORT).show();
            }
        });

        btnBackToLogin.setOnClickListener(v -> {
            finish();
        });
    }
} 