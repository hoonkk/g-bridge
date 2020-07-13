package com.june.commitsmanager;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtCommit, txtFollow, txtCommitMsg, txtName;
    Button btnReturn;
    String id;
    ImageView profile;

    // userinfo

    String name;
    String imgSrc;
    String follower;
    String following;

    // commit

    String count;
    String lastCommit;
    String msg;
    String repository;

    Retrofit retrofit;

    // 리눅스에 올라간 서버 url
    private final String BASE_URL = "http://ec2-18-223-112-230.us-east-2.compute.amazonaws.com:3001";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        btnReturn = (Button)findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener((View.OnClickListener) this);
        txtCommit = (TextView)findViewById(R.id.txtCommit);
        txtCommitMsg = (TextView)findViewById(R.id.txtCommitMsg);
        txtFollow = (TextView)findViewById(R.id.txtFollow);
        txtName = (TextView)findViewById(R.id.txtName);
        profile = (ImageView)findViewById(R.id.profile);
        // 이전 액티비티로부터 id값을 전달받는다.
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // GitHub API 인터페이스 생성
        GithubAPI githubAPI = retrofit.create(GithubAPI.class);

        Map mapUserinfo = new HashMap();
        mapUserinfo.put("id",id);
        mapUserinfo.put("token", token);

        // Userinfo @get 요청
        Call<Userinfo> callUserinfo = githubAPI.userInfo(mapUserinfo);

        callUserinfo.enqueue(new Callback<Userinfo>() {
            @Override
            public void onResponse(Call<Userinfo> call, Response<Userinfo> response) {
                Userinfo userinfos = response.body();
                name = userinfos.getName();
                imgSrc = userinfos.getImgSrc();
                following = userinfos.getFollowing();
                follower = userinfos.getFollower();

                // 오픈소스 Glide를 이용하여 외부 이미지 url을 덧붙여준다.
                Glide.with(getApplicationContext()).load(imgSrc).into(profile);


                txtName.setText(name+"님, 환영해요!");
                txtFollow.setText("팔로워 : " + follower + "   팔로잉 : "+ following);
            }
            @Override
            public void onFailure(Call<Userinfo> call, Throwable t) {
                txtName.setText(t.toString());
            }
        });


        // /commit 요청
        Call<Commit> callCommit = githubAPI.commit(mapUserinfo);
        callCommit.enqueue(new Callback<Commit>() {
            @Override
            public void onResponse(Call<Commit> call, Response<Commit> response) {
                Commit commit = response.body();
                count = commit.getCount();
                lastCommit = commit.getLastCommit();
                repository = commit.getRepository();
                msg = commit.getMsg();

                txtCommit.setText("오늘의 커밋 내역\n" + count);
                int countInteger = Integer.parseInt(count);
                if(countInteger > 0) // 커밋이 하나라도 존재한다면
                {
                    txtCommitMsg.setText("Respository: " + repository +"로\n" +msg +"\n커밋이 확인됬어요.");
                }

            }
            @Override
            public void onFailure(Call<Commit> call, Throwable t) {
            }
        });





    }

    public void onClick(View v) {
        if(v.getId()== R.id.btnReturn) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}