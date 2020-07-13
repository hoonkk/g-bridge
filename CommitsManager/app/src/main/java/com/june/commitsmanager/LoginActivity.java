package com.june.commitsmanager;

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

import java.util.Map;

//import java.util.Currency;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtId;
    EditText edtPw;
    TextView txtWarning;
    Button btnLogin;
    DatabaseReference mDBReference = null;

    Map<String, Map<String, Object>> userInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtId =(EditText)findViewById(R.id.edt_loginId);
        edtPw =(EditText)findViewById(R.id.edt_loginPw);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        txtWarning = (TextView)findViewById(R.id.txtWarning);
        btnLogin.setOnClickListener((View.OnClickListener) this);

        // 데이터 검색
        mDBReference = FirebaseDatabase.getInstance().getReference();
        mDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfo = (Map<String, Map<String, Object>>) dataSnapshot.getValue();
                txtWarning.setText(userInfo.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
    public void onClick(View v) {
        if(v.getId()== R.id.btnLogin) { // validation
            String id = edtId.getText().toString();
            String pw = edtPw.getText().toString();

            try {
                String dataPw = userInfo.get("User_info").get(id).toString();
                // 비밀번호 가공하여 원래 값만 빼온다

                dataPw = dataPw.replace("{", "");
                dataPw = dataPw.replace("}", "");
                dataPw = dataPw.replace("pw=", "");

                if(pw.equals(dataPw)) {// 비밀번호를 올바르게 입력했다면
                    String msg = "정상 로그인 되었습니다.";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                } else { //일치하지 않는다면
                    txtWarning.setText("비밀번호를 잘못 입력했어요.");
                }
            } catch(Exception e) { // 아예 데이터를 빼오는 것이 안된다면 존재하지 않는 아이디임을 알린다.
                txtWarning.setText("존재하지 않는 아이디입니다.");
            }




        }
    }
}