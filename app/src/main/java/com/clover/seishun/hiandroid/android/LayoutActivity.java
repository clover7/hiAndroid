package com.clover.seishun.hiandroid.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.clover.seishun.hiandroid.R;

public class LayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Button v_Btn1 = (Button)findViewById(R.id.v_Btn1);
    }
}
