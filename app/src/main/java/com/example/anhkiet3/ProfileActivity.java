package com.example.anhkiet3;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.anhkiet3.R;
import android.content.Intent;
import android.app.Activity;
import android.provider.MediaStore;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.LinearLayout;
import android.widget.ImageButton;
import android.content.SharedPreferences;

public class ProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1002;
    private ImageView imgAvatar;
    private TextView tvUserName, tvUserPhone, tvOrderCount;
    private Button btnEditAvatar, btnLogout;
    private ImageButton btnBackProfile;
    private LinearLayout btnOrderHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imgAvatar = findViewById(R.id.imgAvatar);
        tvUserName = findViewById(R.id.tvUserName);
        tvUserPhone = findViewById(R.id.tvUserPhone);
        tvOrderCount = findViewById(R.id.tvOrderCount);
        btnEditAvatar = findViewById(R.id.btnEditAvatar);
        btnLogout = findViewById(R.id.btnLogout);
        btnOrderHistory = findViewById(R.id.btnOrderHistory);
        btnBackProfile = findViewById(R.id.btnBackProfile);
        Button btnEditProfile = findViewById(R.id.btnEditProfile);

        // Lấy thông tin user từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        String email = prefs.getString("mock_email", null);
        String name = prefs.getString("mock_fullname", null);
        String phone = prefs.getString("mock_phone", null);

        if (email == null) {
            // Chưa đăng nhập, chuyển về LoginActivity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Ưu tiên hiển thị thông tin từ local nếu có
        if (name != null) tvUserName.setText(name);
        else tvUserName.setText(email);
        if (phone != null) tvUserPhone.setText(phone);
        else tvUserPhone.setText("");

        // Không gọi API lấy user nữa nếu đã có local
        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            intent.putExtra("name", tvUserName.getText().toString());
            intent.putExtra("phone", tvUserPhone.getText().toString());
            intent.putExtra("email", email);
            startActivityForResult(intent, 2001);
        });
        // Đã xóa toàn bộ đoạn code gọi API lấy user từ fakestoreapi.com
        // Chỉ giữ lại logic lấy và hiển thị thông tin user từ SharedPreferences
        // Dữ liệu mẫu
        int orderCount = 3;
        tvOrderCount.setText(String.valueOf(orderCount));
        btnEditAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });
        btnLogout.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
        btnOrderHistory.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, OrderHistoryActivity.class));
        });
        btnBackProfile.setOnClickListener(v -> finish());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                imgAvatar.setImageURI(selectedImage);
            }
        }
        if (requestCode == 2001 && resultCode == Activity.RESULT_OK && data != null) {
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            String email = data.getStringExtra("email");
            if (name != null) tvUserName.setText(name);
            if (phone != null) tvUserPhone.setText(phone);
            // Nếu có TextView email thì cập nhật, nếu không thì bỏ qua
        }
    }
} 