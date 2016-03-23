package com.clover.seishun.hiandroid.android;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.clover.seishun.hiandroid.R;

public class IntentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        TextView intentActivity = (TextView)findViewById(R.id.intentActivity);

        Intent intent = getIntent();
        String strIntent = intent.getStringExtra("reqText");
        if(strIntent != null)
            intentActivity.setText(strIntent);
        /**
         * getIntent()
         * 액티비티로 전달된 인텐트를 구한다. 동작, 데이터 추가 정보가 들어있다.
         *
         * getStringExtra()
         * 해당 메서드를 호출하여 "reqText"라는 이름으로 전달된 문자열을 읽는다.*/

        TextView intentIntro = (TextView)findViewById(R.id.intentIntro);
        intentIntro.setText(R.string.intent_intro);

        TextView intentContents = (TextView)findViewById(R.id.intentContents);
        intentContents.setText(R.string.intent_context);

        TextView intentFooter = (TextView)findViewById(R.id.intentFooter);
        intentFooter.setText(R.string.intent_footer);

        Button btnIntent = (Button)findViewById(R.id.btnIntent);
        btnIntent.setText(R.string.btn_Finish);

        Button btnImplicitIntent1 = (Button)findViewById(R.id.btnImplicitIntent1);
        btnImplicitIntent1.setText(R.string.view_web);

        Button btnImplicitIntent2 = (Button)findViewById(R.id.btnImplicitIntent2);
        btnImplicitIntent2.setText(R.string.view_call);

        Button btnImplicitIntent3 = (Button)findViewById(R.id.btnImplicitIntent3);
        btnImplicitIntent3.setText(R.string.view_browser);

//        Button btnImplicitIntent3 = (Button)findViewById(R.id.btnImplicitIntent3);
//        btnImplicitIntent3.setText(R.string.view_web);
//        btnImplicitIntent3.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(IntentActivity.this, WebViewActivity.class);
//                startActivity(intent);
//            }
//        });

        btnIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnIntent:
                        Intent intent = new Intent();
                        intent.putExtra("resText", "RESULT_OK");
                        setResult(RESULT_OK,intent);
                        finish();
                        break;
                }
            }
        });

    }

    public void mOnClick(View v){
        Intent intent;
        switch(v.getId()){
            case R.id.btnImplicitIntent1:
                /* 앱이 중지됨.. */
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setComponent(new ComponentName( "com.android.browser","com.android.browser.BrowserActivity"));
                startActivity(intent);
                break;
            case R.id.btnImplicitIntent2:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-000-0000"));
                startActivity(intent);
                break;
            case R.id.btnImplicitIntent3:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(intent);
                break;

        }
    }

    /** WebView
     * 1. permission.INTERNET
     * 2.
     *
     * */


}
