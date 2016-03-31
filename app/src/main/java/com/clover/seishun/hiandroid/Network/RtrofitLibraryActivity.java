package com.clover.seishun.hiandroid.Network;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.clover.seishun.hiandroid.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.ViewById;

public class RtrofitLibraryActivity extends AppCompatActivity {


    private static final String API_URL = "https://api.github.com";
    @ViewById(R.id.btnCall)
    Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtrofit_library);
        init();
    }

    private void init() {
        btnCall.setText("Web Service Call");
    }


    @Click(R.id.btnCall)
    public void webServiceCallBtnClicked(){
        Toast.makeText(this, "@Click(R.id.btnCall)", Toast.LENGTH_SHORT).show();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(API_URL)
//                .addConverterFactory(JacksonConvertFactory.create())
//                .build();
//
//        // Create an instance of our GitHub API interface.
//        GitHubService.GitHub github = retrofit.create(GitHubService.GitHub.class);
//
//        // Create a call instance for looking up Retrofit contributors.
//        Call<List<GitHubService.Contributor>> call = github.contributors("square", "retrofit");
//
//        // Fetch and print a list of the contributors to the library.
//        List<GitHubService.Contributor> contributors = null;
//        try {
//            contributors = call.execute().body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for (GitHubService.Contributor contributor : contributors) {
//            System.out.println(contributor.login + " (" + contributor.contributions + ")");
//        }
    }
}
