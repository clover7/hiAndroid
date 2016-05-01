package com.swoa.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.clover.seishun.hiandroid.R;

import java.util.HashMap;

public class SwoaMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swoa_main);

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

//
//        Button SaveCoffeeActiveData = (Button)findViewById(R.id.swoa_Btn5);
//        SaveCoffeeActiveData.setText("ActiveData");
//        SaveCoffeeActiveData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveCoffeeActiveData();
//            }
//        });
//
//        Button SaveCoffeeCurrentData = (Button)findViewById(R.id.swoa_Btn4);
//        SaveCoffeeCurrentData.setText("CurrentData");
//        SaveCoffeeCurrentData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveCoffeeCurrentData();
//            }
//        });
//
//        Button SaveCoffeeDayData = (Button)findViewById(R.id.swoa_Btn6);
//        SaveCoffeeDayData.setText("DayData");
//        SaveCoffeeDayData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveCoffeeDayData();
//            }
//        });
    }


    private void createUser() {
        EditText editUserID = (EditText)findViewById(R.id.editUserID);
        EditText editUserPW = (EditText)findViewById(R.id.editUserPW);
        EditText editUserFM = (EditText)findViewById(R.id.editUserFM);
        EditText editUserLM = (EditText)findViewById(R.id.editUserLM);

        String UserID = editUserID.getText().toString();
        String UserPW = editUserPW.getText().toString();
        String UserFM = editUserFM.getText().toString();
        String UserLM = editUserLM.getText().toString();

        Intent intent = new Intent(this, SWOAWebserviceActivity.class);
        HashMap<String, String> params= new HashMap<>();
        params.put("userId",    UserID);
        params.put("password",  UserPW);
        params.put("firstName", UserFM);
        params.put("lastName",  UserLM);

        intent.putExtra("params", params);
        intent.putExtra("btnClick",1);
//        intent.putExtra("btnClick",R.id.swoa_Btn1);
        startActivity(intent);
    }

    private void loginUserInfo(){
        EditText editUserID = (EditText)findViewById(R.id.editUserID);
        EditText editUserPW = (EditText)findViewById(R.id.editUserPW);

        String UserID = editUserID.getText().toString();
        String UserPW = editUserPW.getText().toString();

        Intent intent = new Intent(this, SWOAWebserviceActivity.class);
        HashMap<String, String> params= new HashMap<>();
        params.put("userId",UserID);
        params.put("password", UserPW);

        intent.putExtra("params", params);
        intent.putExtra("btnClick",2);
//        intent.putExtra("btnClick",R.id.swoa_Btn2);
        startActivity(intent);
    }


    private void saveCoffeeCurrentData() {
//        - 당일 누적 데이터 저장 : SaveCoffeeCurrentData(string value)
//        input param 예){"insung_seq":2757,"steps":"100","calorie":34,"distance":10,"exer_time":10,"max_conti_steps":200,"max_exer_time":15,"max_relax_time":20,"max_velocity":12}
    }

    private void saveCoffeeActiveData() {
//        - 30분 측정데이터 저장 : SaveCoffeeActiveData(string value)
//        input param 예){"insung_seq":2757,"measure_date":"20160405150210","steps":100,"calorie":34,"distance":10,"exer_time":10}
    }

    private void saveCoffeeDayData() {
//        - 일자별 데이터 저장 : SaveCoffeeDayData(string value)
//        input param 예){"insung_seq":2757,"measure_date":"20160405150210","max_conti_steps":200,"max_exer_time":15,"max_relax_time":20,"max_velocity":12}

    }
}
