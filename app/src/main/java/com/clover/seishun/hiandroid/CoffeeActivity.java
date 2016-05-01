package com.clover.seishun.hiandroid;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_coffee)
public class CoffeeActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();
        btnH_btn1.setText(R.string.device_connect);
        btnH_btn2.setText(R.string.time_setting);
        btnH_btn3.setText(R.string.user_info_setting);
    }
    @ViewById(R.id.h_Btn1)
    public Button btnH_btn1;
    @ViewById(R.id.h_Btn3)
    public Button btnH_btn2;
    @ViewById(R.id.h_Btn3)
    public Button btnH_btn3;

    @AfterViews
    protected void settingView() {
        btnH_btn1.setText(R.string.device_connect);
        btnH_btn2.setText(R.string.time_setting);
        btnH_btn3.setText(R.string.user_info_setting);
    }

    @Click(R.id.h_Btn1)
    public void onClick_btn1() {
        Toast.makeText(CoffeeActivity.this, R.string.device_connect, Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.h_Btn2)
    public void onClick_btn2() {
        Toast.makeText(CoffeeActivity.this, R.string.time_setting, Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.h_Btn3)
    public void onClick_btn3() {
        Toast.makeText(CoffeeActivity.this, R.string.user_info_setting, Toast.LENGTH_SHORT).show();
    }
}
