package com.clover.seishun.hiandroid.Annotation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clover.seishun.hiandroid.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_annotation_start)
public class AnnotationStart extends AppCompatActivity {

    private static final int REQUEST_START = 1;
    @ViewById(R.id.myInput)
    EditText myInput;

    @ViewById(R.id.myTextView)
    TextView textView;

    @ViewById(R.id.btn1)
    Button btnActivity;

    @Click({R.id.myButton ,R.id.btn1})
    void myButton(View view) {
        switch (view.getId()){
            case R.id.myButton:
                String name = myInput.getText().toString();
                textView.setText("Hello " + name);
                break;
            case R.id.btn1:
//                AnnotationActivity_.intent(getApplicationContext()).start();
                Intent intent = new Intent(this, AnnotationActivity_.class);
                intent.putExtra("myMessage", "hello Annotation");
                startActivityForResult(intent, REQUEST_START);
                break;
        }
    }
    @AfterViews
    public void init() {
       textView.setText("init");
        btnActivity.setText("call Activity");
    }

    @OnActivityResult(REQUEST_START)
    public void onResult(int resultCode, Intent data, @OnActivityResult.Extra String value) {
        switch (resultCode){
            case RESULT_OK:
                Toast.makeText(this, data.getStringExtra("resText").toString() ,Toast.LENGTH_LONG).show();
                Toast.makeText(this, "@OnActivityResult.Extra : " + value.toString() ,Toast.LENGTH_LONG).show();
//                Toast.makeText(this, "RESULT_OK" ,Toast.LENGTH_LONG).show();
                break;
            case RESULT_CANCELED:
                Toast.makeText(this,  "RESULT_CANCELED" ,Toast.LENGTH_LONG).show();
                break;

        }
    }





}
