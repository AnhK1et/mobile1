package com.example.anhkiet3;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryApi {
    @GET("products/categories")
    Call<List<String>> getCategories();
} 