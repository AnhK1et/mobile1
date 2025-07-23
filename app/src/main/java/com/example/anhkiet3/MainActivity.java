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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Product> cart = new ArrayList<>();
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
        List<String> categoryList = new ArrayList<>();
        categoryList.add("Tất cả"); // Luôn có "Tất cả"

        // Sản phẩm
        RecyclerView rvProduct = findViewById(R.id.rvProduct);
        List<Product> allProducts = new ArrayList<>();
        List<Product> filteredProducts = new ArrayList<>(allProducts); // ban đầu hiển thị tất cả
        ProductAdapter productAdapter = new ProductAdapter(filteredProducts);
        productAdapter.setOnAddToCartListener(product -> {
            boolean found = false;
            for (Product p : cart) {
                String name1 = p.getName() != null ? p.getName() : p.getTitle();
                String name2 = product.getName() != null ? product.getName() : product.getTitle();
                if (Objects.equals(name1, name2)) {
                    p.setQuantity(p.getQuantity() + 1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                Product newProduct = new Product(product.getName(), product.getPrice(), product.getImageResId(), product.getCategory(), product.getDescription());
                newProduct.setOldPrice(product.getOldPrice());
                newProduct.setQuantity(1);
                newProduct.setTitle(product.getTitle());
                newProduct.setImage(product.getImage());
                newProduct.setPriceDouble(product.getPriceDouble());
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductApi api = retrofit.create(ProductApi.class);
        CategoryApi categoryApi = retrofit.create(CategoryApi.class);

        // Lấy sản phẩm từ FakeStoreAPI khi vào trang chủ
        api.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allProducts.clear();
                    allProducts.addAll(response.body());
                    filteredProducts.clear();
                    filteredProducts.addAll(response.body());
                    productAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Không thể lấy dữ liệu online, vui lòng kiểm tra kết nối!", Toast.LENGTH_LONG).show();
            }
        });

        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryList, categoryName -> {
            if (categoryName.equals("Tất cả")) {
                api.getProducts().enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            filteredProducts.clear();
                            filteredProducts.addAll(response.body());
                            productAdapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        filteredProducts.clear();
                        filteredProducts.addAll(allProducts);
                        productAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Không thể lấy dữ liệu online, đang hiển thị dữ liệu mẫu.", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                api.getProductsByCategory(categoryName).enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            filteredProducts.clear();
                            filteredProducts.addAll(response.body());
                            productAdapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        filteredProducts.clear();
                        // Lọc offline nếu mất mạng
                        for (Product p : allProducts) {
                            if (p.getCategory().equals(categoryName)) {
                                filteredProducts.add(p);
                            }
                        }
                        productAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Không thể lấy dữ liệu online, đang hiển thị dữ liệu mẫu.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        rvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCategory.setAdapter(categoryAdapter);

        ImageView imgCart = findViewById(R.id.imgCart);
        imgCart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });

        ImageView btnProfile = findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().toLowerCase();
                filteredProducts.clear();
                if (keyword.isEmpty()) {
                    filteredProducts.addAll(allProducts);
                } else {
                    for (Product p : allProducts) {
                        if (p.getName().toLowerCase().contains(keyword)) {
                            filteredProducts.add(p);
                        }
                    }
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        // Gọi API lấy danh mục động
        categoryApi.getCategories().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList.clear();
                    categoryList.add("Tất cả");
                    categoryList.addAll(response.body());
                    categoryAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                // Nếu lỗi, giữ danh mục mẫu
            }
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
                    String name1 = p.getName() != null ? p.getName() : p.getTitle();
                    String name2 = product.getName() != null ? product.getName() : product.getTitle();
                    if (Objects.equals(name1, name2)) {
                        p.setQuantity(p.getQuantity() + 1);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Product newProduct = new Product(product.getName(), product.getPrice(), product.getImageResId(), product.getCategory(), product.getDescription());
                    newProduct.setOldPrice(product.getOldPrice());
                    newProduct.setQuantity(1);
                    newProduct.setTitle(product.getTitle());
                    newProduct.setImage(product.getImage());
                    newProduct.setPriceDouble(product.getPriceDouble());
                    cart.add(newProduct);
                }
            }
        }
    }
}