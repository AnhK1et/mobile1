package com.example.anhkiet3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.widget.CheckBox;
import java.text.NumberFormat;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    private List<Product> cartProducts;
    private CartAdapter cartAdapter;
    private TextView tvTotalPrice;
    private CheckBox checkboxSelectAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView rvCart = findViewById(R.id.rvCart);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        cartProducts = (List<Product>) getIntent().getSerializableExtra("cart");
        if (cartProducts == null) cartProducts = new ArrayList<>();

        cartAdapter = new CartAdapter(cartProducts, new CartAdapter.OnCartActionListener() {
            @Override
            public void onQuantityChanged() {
                updateTotalPrice();
            }
            @Override
            public void onItemRemoved(int position) {
                cartAdapter.notifyDataSetChanged();
                updateTotalPrice();
            }
        });

        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.setAdapter(cartAdapter);
        updateTotalPrice();

        // Nút quay lại bằng icon trên Toolbar
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        // Nút Thanh toán (có thể thêm logic ở đây)
        Button btnCheckout = findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(v -> {
            // TODO: Xử lý thanh toán
        });

        checkboxSelectAll = findViewById(R.id.checkboxSelectAll);
        checkboxSelectAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (Product p : cartProducts) {
                p.setSelected(isChecked);
            }
            cartAdapter.notifyDataSetChanged();
        });
    }

    private void updateTotalPrice() {
        long total = 0;
        for (Product p : cartProducts) {
            try {
                String priceStr = p.getPrice().replaceAll("[^0-9]", "");
                long price = priceStr.isEmpty() ? 0 : Long.parseLong(priceStr);
                total += price * p.getQuantity();
            } catch (Exception e) {
                // Bỏ qua nếu lỗi parse
            }
        }
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formatted = formatter.format(total) + "đ";
        tvTotalPrice.setText("Tổng tiền: " + formatted);
    }
} 