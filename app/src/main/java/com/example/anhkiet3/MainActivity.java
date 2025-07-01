package com.example.anhkiet3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Product> cart = new ArrayList<>();
    private static final int REQUEST_PRODUCT_DETAIL = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Banner demo
        ImageView imgBanner = findViewById(R.id.imgBanner);
        imgBanner.setImageResource(R.drawable.banner); // Đổi thành ảnh banner thực tế nếu có, ví dụ: R.drawable.banner

        // Tìm kiếm (chưa xử lý logic)
        EditText etSearch = findViewById(R.id.etSearch);

        // Danh mục
        RecyclerView rvCategory = findViewById(R.id.rvCategory);
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Tất cả"));
        categoryList.add(new Category("iPhone"));
        categoryList.add(new Category("Samsung"));
        categoryList.add(new Category("Redmi"));
        categoryList.add(new Category("Oppo"));

        // Sản phẩm
        RecyclerView rvProduct = findViewById(R.id.rvProduct);
        List<Product> allProducts = new ArrayList<>();
        allProducts.add(new Product("iPhone 15 Pro Max", "34.990.000đ", R.drawable.iphone, "iPhone"));
        allProducts.add(new Product("Samsung Galaxy S24 Ultra", "28.990.000đ", R.drawable.samsung, "Samsung"));
        allProducts.add(new Product("Xiaomi 13T Pro", "12.990.000đ", R.drawable.redmi, "Redmi"));
        allProducts.add(new Product("OPPO Reno10", "10.990.000đ", R.drawable.oppo, "Oppo"));
        // ... thêm sản phẩm nếu muốn

        List<Product> filteredProducts = new ArrayList<>(allProducts); // ban đầu hiển thị tất cả
        ProductAdapter productAdapter = new ProductAdapter(filteredProducts);
        productAdapter.setOnAddToCartListener(product -> {
            boolean found = false;
            for (Product p : cart) {
                if (p.getName().equals(product.getName())) {
                    p.setQuantity(p.getQuantity() + 1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                Product newProduct = new Product(product.getName(), product.getPrice(), product.getImageResId(), product.getCategory());
                newProduct.setOldPrice(product.getOldPrice());
                newProduct.setQuantity(1);
                cart.add(newProduct);
            }
            Toast.makeText(this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
        });
        rvProduct.setLayoutManager(new GridLayoutManager(this, 2));
        rvProduct.setAdapter(productAdapter);

        // Sửa ProductAdapter để mở ProductDetailActivity bằng startActivityForResult
        productAdapter.setOnItemClickListener(product -> {
            Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
            intent.putExtra("product", product);
            startActivityForResult(intent, REQUEST_PRODUCT_DETAIL);
        });

        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryList, categoryName -> {
            filteredProducts.clear();
            if (categoryName.equals("Tất cả")) {
                filteredProducts.addAll(allProducts);
            } else {
                for (Product p : allProducts) {
                    if (p.getCategory().equals(categoryName)) {
                        filteredProducts.add(p);
                    }
                }
            }
            productAdapter.notifyDataSetChanged();
        });
        rvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCategory.setAdapter(categoryAdapter);

        ImageView imgCart = findViewById(R.id.imgCart);
        imgCart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            intent.putExtra("cart", cart);
            startActivity(intent);
        });

        // Profile demo
        ImageView imgAvatar = findViewById(R.id.imgAvatar);
        TextView tvUserName = findViewById(R.id.tvUserName);
        TextView tvUserEmail = findViewById(R.id.tvUserEmail);
        ImageButton btnEditProfile = findViewById(R.id.btnEditProfile);
        // Gán dữ liệu demo
        tvUserName.setText("Admin");
        tvUserEmail.setText("admin@gmail.com");
        // Xử lý nút chỉnh sửa
        btnEditProfile.setOnClickListener(v -> {
            Toast.makeText(this, "Chức năng chỉnh sửa profile sẽ cập nhật sau!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PRODUCT_DETAIL && resultCode == RESULT_OK && data != null) {
            Product product = (Product) data.getSerializableExtra("add_to_cart");
            if (product != null) {
                boolean found = false;
                for (Product p : cart) {
                    if (p.getName().equals(product.getName())) {
                        p.setQuantity(p.getQuantity() + 1);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Product newProduct = new Product(product.getName(), product.getPrice(), product.getImageResId(), product.getCategory());
                    newProduct.setOldPrice(product.getOldPrice());
                    newProduct.setQuantity(1);
                    cart.add(newProduct);
                }
            }
        }
    }
}