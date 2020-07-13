package com.june.commitsmanager;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GithubAPI {
    // GET/POST/DELETE/PUT 메소드들을 인터페이스에 구현하여 사용할 수 있다.
    @GET("/userinfo")
    Call<Userinfo> userInfo(
            @QueryMap Map<String, String> option);
    @GET("/commit")
    Call<Commit> commit(
            @QueryMap Map<String, String> option);
}
