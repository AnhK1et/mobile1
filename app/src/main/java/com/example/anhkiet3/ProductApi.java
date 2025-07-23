package com.example.anhkiet3;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductApi {
    @GET("products")
    Call<List<Product>> getProducts();

    @GET("products/category/{category}")
    Call<List<Product>> getProductsByCategory(@Path("category") String category);

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") int id);
} 