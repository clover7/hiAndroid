package com.swoa.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.clover.seishun.hiandroid.R;

import java.util.HashMap;

public class SwoaMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swoa_main);

        Button POST = (Button)findViewById(R.id.swoa_Btn3);
        POST.setText("POST");
        POST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postTest();
            }
        });

        Button createUser = (Button)findViewById(R.id.swoa_Btn1);
        createUser.setText("createUser");
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        Button loginUser = (Button)findViewById(R.id.swoa_Btn2);
        loginUser.setText("loginUser");
        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserInfo();
            }
        });
    }

    private void createUser() {
        String userId = "INswoapat1";
        String password = "1234";

        Intent intent = new Intent(this, SWOAWebserviceActivity.class);
        HashMap<String, String> params= new HashMap<>();
        params.put("userId",userId);
        params.put("password", password);

        intent.putExtra("createUser",params);
        startActivity(intent);
    }

    private void postTest() {
        String userId = "INswoapat1";
        String password = "1234";

        Intent intent = new Intent(this, SWOAWebserviceActivity.class);
        HashMap<String, String> params= new HashMap<>();
        params.put("userId",userId);
        params.put("password", password);

        intent.putExtra("postTest",params);
        startActivity(intent);
    }


    private void loginUserInfo(){
        String userId = "INswoapat1";
        String password = "1234";

        Intent intent = new Intent(this, SWOAWebserviceActivity.class);
        HashMap<String, String> params= new HashMap<>();
        params.put("userId",userId);
        params.put("password", password);

        intent.putExtra("loginUser",params);
        startActivity(intent);
    }
}
