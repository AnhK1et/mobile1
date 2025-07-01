package com.example.anhkiet3;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {
    private Product product;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        product = (Product) getIntent().getSerializableExtra("product");
        if (product == null) {
            finish();
            return;
        }

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        ImageView imgProduct = findViewById(R.id.imgProductDetail);
        TextView tvName = findViewById(R.id.tvProductNameDetail);
        TextView tvPrice = findViewById(R.id.tvProductPriceDetail);
        TextView tvOldPrice = findViewById(R.id.tvProductOldPriceDetail);
        TextView tvDesc = findViewById(R.id.tvProductDescDetail);
        Button btnAddToCart = findViewById(R.id.btnAddToCartDetail);

        imgProduct.setImageResource(product.getImageResId());
        tvName.setText(product.getName());
        tvPrice.setText(product.getPrice());
        if (product.getOldPrice() != null && !product.getOldPrice().isEmpty()) {
            tvOldPrice.setText(product.getOldPrice());
            tvOldPrice.setPaintFlags(tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            tvOldPrice.setText("");
        }
        tvDesc.setText("Mô tả sản phẩm sẽ hiển thị ở đây."); // Có thể lấy từ Product nếu có

        btnAddToCart.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("add_to_cart", product);
            setResult(RESULT_OK, resultIntent);
            Toast.makeText(this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
} 