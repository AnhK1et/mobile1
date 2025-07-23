package com.example.anhkiet3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Paint;
import android.widget.Toast;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.bumptech.glide.Glide;
import android.widget.ImageView;
import android.content.Intent;

public class CheckoutActivity extends AppCompatActivity {
    private LinearLayout tabInfo, tabPayment;
    private ScrollView layoutInfo, layoutPayment;
    private Button continueBtn, placeOrderBtn;
    private TextView tabInfoText, tabPaymentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "CheckoutActivity opened", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_checkout);

        tabInfoText = findViewById(R.id.tab_info);
        tabPaymentText = findViewById(R.id.tab_payment);
        layoutInfo = findViewById(R.id.layout_info);
        layoutPayment = findViewById(R.id.layout_payment);
        continueBtn = findViewById(R.id.checkout_continue_btn);
        placeOrderBtn = findViewById(R.id.checkout_place_order_btn);

        // Lấy dữ liệu sản phẩm từ FakeStoreAPI (ví dụ id=1)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductApi api = retrofit.create(ProductApi.class);
        api.getProduct(1).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Product product = response.body();
                    TextView name = findViewById(R.id.checkout_product_name);
                    name.setText(product.getTitle());
                    TextView price = findViewById(R.id.checkout_product_price);
                    price.setText(product.getPrice() + " USD");
                    TextView oldPrice = findViewById(R.id.checkout_product_old_price);
                    oldPrice.setText("");
                    ImageView img = findViewById(R.id.checkout_product_image);
                    Glide.with(CheckoutActivity.this).load(product.getImage()).into(img);

                    // Cập nhật giá động cho phần thanh toán
                    TextView subtotal = findViewById(R.id.checkout_payment_subtotal);
                    subtotal.setText(product.getPrice() + " USD");
                    TextView total = findViewById(R.id.checkout_payment_total);
                    total.setText(product.getPrice() + " USD");
                    TextView quantity = findViewById(R.id.checkout_payment_quantity);
                    quantity.setText("1");
                }
            }
            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this, "Không thể lấy dữ liệu sản phẩm!", Toast.LENGTH_SHORT).show();
            }
        });

        // Tab click listeners
        tabInfoText.setOnClickListener(v -> showInfoTab());
        tabPaymentText.setOnClickListener(v -> showPaymentTab());
        continueBtn.setOnClickListener(v -> showPaymentTab());
        placeOrderBtn.setOnClickListener(v -> {
            Toast.makeText(CheckoutActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
            // Xóa toàn bộ giỏ hàng sau khi đặt hàng
            MainActivity.cart.clear();
            // Chuyển về trang giỏ hàng
            startActivity(new Intent(CheckoutActivity.this, CartActivity.class));
            finish();
        });

        // Gạch ngang giá cũ
        TextView oldPrice = findViewById(R.id.checkout_product_old_price);
        oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        showInfoTab();
    }

    private void showInfoTab() {
        layoutInfo.setVisibility(View.VISIBLE);
        layoutPayment.setVisibility(View.GONE);
        tabInfoText.setTextColor(getResources().getColor(R.color.red));
        tabPaymentText.setTextColor(getResources().getColor(R.color.gray));
    }

    private void showPaymentTab() {
        layoutInfo.setVisibility(View.GONE);
        layoutPayment.setVisibility(View.VISIBLE);
        tabInfoText.setTextColor(getResources().getColor(R.color.gray));
        tabPaymentText.setTextColor(getResources().getColor(R.color.red));
    }
} 