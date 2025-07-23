package com.example.anhkiet3;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface UserApi {
    @GET("users/{id}")
    Call<User> getUser(@Path("id") int id);

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("users")
    Call<User> register(@Body RegisterRequest request);
} 