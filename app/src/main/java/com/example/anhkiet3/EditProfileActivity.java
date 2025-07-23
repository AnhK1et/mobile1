package com.example.anhkiet3;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.anhkiet3.R;
import android.content.Intent;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Button;

public class EditProfileActivity extends AppCompatActivity {
    private ImageView imgEditAvatar;
    private EditText etEditName, etEditPhone, etEditEmail;
    private Button btnSaveProfile, btnCancelEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        imgEditAvatar = findViewById(R.id.imgEditAvatar);
        etEditName = findViewById(R.id.etEditName);
        etEditPhone = findViewById(R.id.etEditPhone);
        etEditEmail = findViewById(R.id.etEditEmail);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnCancelEdit = findViewById(R.id.btnCancelEdit);

        // Nhận dữ liệu truyền vào từ intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String email = intent.getStringExtra("email");
        if (name != null) etEditName.setText(name);
        if (phone != null) etEditPhone.setText(phone);
        if (email != null) etEditEmail.setText(email);

        btnSaveProfile.setOnClickListener(v -> {
            // Xử lý lưu thông tin (gửi về server hoặc trả về ProfileActivity)
            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", etEditName.getText().toString());
            resultIntent.putExtra("phone", etEditPhone.getText().toString());
            resultIntent.putExtra("email", etEditEmail.getText().toString());
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });

        btnCancelEdit.setOnClickListener(v -> finish());
    }
} 