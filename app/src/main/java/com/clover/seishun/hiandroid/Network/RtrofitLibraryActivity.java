package com.clover.seishun.hiandroid.Network;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.clover.seishun.hiandroid.R;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class RtrofitLibraryActivity extends AppCompatActivity {

    private static final String API_URL = "https://api.github.com";
    TextView txtNetworkContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtrofit_library);
        init();
    }

    private void init() {
        Button btnCall = (Button)findViewById(R.id.btnCall);
        btnCall.setText("Web Service Call");

        txtNetworkContext = (TextView)findViewById(R.id.txtNetworkContext);
    }

    public void rtroFitClick(View v){
        switch (v.getId()) {
            case R.id.btnCall:
                Toast.makeText(this, "@Click(R.id.btnCaall)", Toast.LENGTH_SHORT).show();
                webServiceCallBtnClicked();
                break;
        }
    }

    //@Click(R.id.btnCall)
    public void webServiceCallBtnClicked(){
        Toast.makeText(this, "@Click(R.id.btnCal1l)", Toast.LENGTH_SHORT).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(JacksonConvertFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        GitHubService.GitHub github = retrofit.create(GitHubService.GitHub.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<List<GitHubService.Contributor>> call = github.contributors("square", "retrofit");

        // Fetch and print a list of the contributors to the library.
        List<GitHubService.Contributor> contributors = null;
        try {
            contributors = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (GitHubService.Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
            txtNetworkContext.setText(contributor.login + " (" + contributor.contributions + ")");
        }
    }
}
