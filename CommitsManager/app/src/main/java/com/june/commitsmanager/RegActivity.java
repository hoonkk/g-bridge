package com.june.commitsmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

//import java.util.Currency;

public class RegActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtId;
    EditText edtPw1;
    EditText edtPw2;
    TextView txtNotice;
    Button btnSubmit;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        edtId =(EditText)findViewById(R.id.edtId);
        edtPw1 =(EditText)findViewById(R.id.edtPw1);
        edtPw2 =(EditText)findViewById(R.id.edtPw2);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        txtNotice = (TextView)findViewById(R.id.txtNotice);
        btnSubmit.setOnClickListener((View.OnClickListener) this);


        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                String text = dataSnapshot.getValue(String.class);
//                txtNotice.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void onClick(View v) {
        if(v.getId()==R.id.btnSubmit) { // validation
            if (!edtPw2.getText().toString().equals(edtPw1.getText().toString())) {
                txtNotice.setText("비밀번호가 일치하지 않아요. 다시 입력해주세요.");
            }
            else {
                String msg = "가입이 완료되었습니다";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                HashMap<String, Object> childUpdates = null;
                Map<String, Object> m1 = new HashMap<String, Object>();
                String id = edtId.getText().toString();
                String pw = edtPw2.getText().toString();
                m1.put("pw", pw);
                childUpdates = new HashMap<>();
                childUpdates.put("/User_info/" + id, m1);
                mRootRef.updateChildren(childUpdates);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }
}