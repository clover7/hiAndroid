package com.clover.seishun.hiandroid.Annotation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.clover.seishun.hiandroid.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity
public class AnnotationActivity extends AppCompatActivity {

    @Extra
    String myMessage;

    @ViewById(R.id.txtAnnotation)
    TextView txtAnnotation;

    @ViewById(R.id.btnBack)
    Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
    }

    @AfterViews
    public void init(){
        btnBack.setText("BACK");
        txtAnnotation.setText(myMessage);
    }

    @Click(R.id.btnBack)
    public void onClick(){
            Intent intent = new Intent();
            intent.putExtra("resText", "RESULT_OK");
            setResult(RESULT_OK,intent);
            finish();

    }
}
