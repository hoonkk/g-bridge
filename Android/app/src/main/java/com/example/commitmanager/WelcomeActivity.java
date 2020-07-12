package com.example.commitmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnNext =(Button)findViewById(R.id.btnNext);
        btnNext.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View v) {
        if(v.getId()==R.id.btnNext) {
            Intent intent = new Intent(getApplicationContext(), RegActivity.class);
            startActivity(intent);
        }
    }
}