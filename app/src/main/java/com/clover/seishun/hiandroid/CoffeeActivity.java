package com.clover.seishun.hiandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CoffeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);

        Button btnH_btn1 = (Button)findViewById(R.id.h_Btn1);
        btnH_btn1.setText(R.string.device_connect);
        btnH_btn1.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Toast.makeText(CoffeeActivity.this, R.string.device_connect, Toast.LENGTH_SHORT).show();
                 }
             }
        );

        Button btnH_btn2 =  (Button)findViewById(R.id.h_Btn2);
        btnH_btn2.setText(R.string.time_setting);
        btnH_btn2.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Toast.makeText(CoffeeActivity.this, R.string.time_setting, Toast.LENGTH_SHORT).show();
                 }
             }
        );

        Button btnH_btn3 =  (Button)findViewById(R.id.h_Btn3);
        btnH_btn3.setText(R.string.user_info_setting);
        btnH_btn3.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Toast.makeText(CoffeeActivity.this, R.string.user_info_setting, Toast.LENGTH_SHORT).show();
                 }
             }
        );
    }
}
