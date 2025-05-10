package com.example.nestjs_firebase_admin.api;

import com.example.nestjs_firebase_admin.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/")
    Call<List<User>> getUsers();

    @POST("/")
    Call<User> createUser(@Body User user);

    @PUT("/update/{id}")
    Call<User> updateUser(@Path("id") String id, @Body User user);

    @DELETE("/delete/{id}")
    Call<Void> deleteUser(@Path("id") String id);
}