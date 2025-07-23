package com.example.anhkiet3;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.Intent;
import com.example.anhkiet3.User;
import com.example.anhkiet3.RegisterRequest;
import com.example.anhkiet3.UserApi;
import com.example.anhkiet3.LoginRequest;
import com.example.anhkiet3.LoginResponse;
import com.example.anhkiet3.MainActivity;
import static android.content.Context.MODE_PRIVATE;
import com.example.anhkiet3.R;

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
        ImageButton btnBackToLogin = findViewById(R.id.btnBackToLogin);

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
            // Đăng ký user động qua API
            String[] nameParts = fullName.split(" ", 2);
            String firstname = nameParts.length > 0 ? nameParts[0] : "";
            String lastname = nameParts.length > 1 ? nameParts[1] : "";
            RegisterRequest.Name name = new RegisterRequest.Name(firstname, lastname);
            RegisterRequest.Geolocation geo = new RegisterRequest.Geolocation("0.0000", "0.0000");
            RegisterRequest.Address address = new RegisterRequest.Address("Hanoi", "My Dinh", 1, "10000", geo);
            String username = email.split("@")[0];
            RegisterRequest request = new RegisterRequest(email, username, password, name, address, phone);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://fakestoreapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UserApi userApi = retrofit.create(UserApi.class);
            userApi.register(request).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Lưu thông tin tài khoản vào SharedPreferences để mock đăng nhập bằng email
                        getSharedPreferences("MyApp", MODE_PRIVATE)
                            .edit()
                            .putString("mock_email", email)
                            .putString("mock_password", password)
                            .apply();
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công! Bạn có thể đăng nhập.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnBackToLogin.setOnClickListener(v -> {
            finish();
        });
    }
} 