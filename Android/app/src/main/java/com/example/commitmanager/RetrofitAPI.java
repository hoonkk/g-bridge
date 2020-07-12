package com.example.commitmanager;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPI {

    @GET("/userinfo")
    Call<Userinfo> getUserInfo(@Query("username") String username);

    @GET("/commit")
    Call<Commit> getCommit(@Query("username") String username);
}
