package com.june.commitsmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStart, btnExit, btnMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart =(Button)findViewById(R.id.btnStart);
        btnExit =(Button)findViewById(R.id.btnExit);
        btnMain =(Button)findViewById(R.id.btnMain);
        btnStart.setOnClickListener((View.OnClickListener) this);
        btnExit.setOnClickListener((View.OnClickListener) this);
        btnMain.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View v) {
        if(v.getId()== R.id.btnStart) {
            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(intent);
        }
        else if(v.getId()== R.id.btnMain) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
        else { // 종료 버튼 누를 시
            finish();
        }
    }
}