package com.example.anhkiet3;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etFullName = findViewById(R.id.etFullName);
        EditText etBirthDate = findViewById(R.id.etBirthDate);
        EditText etPhone = findViewById(R.id.etPhone);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnBackToLogin = findViewById(R.id.btnBackToLogin);

        etBirthDate.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) ->
                        etBirthDate.setText(String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year1)),
                year, month, day);
            datePickerDialog.show();
        });

        btnRegister.setOnClickListener(v -> {
            String fullName = etFullName.getText().toString();
            String birthDate = etBirthDate.getText().toString();
            String phone = etPhone.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();

            if (fullName.isEmpty() || birthDate.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin bắt buộc", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 6 || !password.matches(".*[A-Za-z].*") || !password.matches(".*\\d.*")) {
                Toast.makeText(this, "Mật khẩu phải tối thiểu 6 ký tự, có ít nhất 1 chữ và 1 số", Toast.LENGTH_SHORT).show();
                return;
            }
            // TODO: Xử lý đăng ký (gửi dữ liệu lên server...)
            Toast.makeText(this, "Đăng ký thành công (demo)", Toast.LENGTH_SHORT).show();
        });

        btnBackToLogin.setOnClickListener(v -> {
            finish();
        });
    }
} 