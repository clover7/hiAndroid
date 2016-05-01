package com.clover.seishun.hiandroid.Network;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.clover.seishun.hiandroid.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.clover.seishun.hiandroid.R.layout;

public class BasicNetworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_basic_network);
        init();
    }

    private static final String API_URL = "https://api.github.com";
    private static final String TAG = RetrofitLibraryActivity.class.getSimpleName();
    TextView txtNetworkContext;
    boolean mRaw;
    ProgressDialog mProgress;
    List<Contributor> responseData = null;
    List<Contributor> ContributorData;

    Message message;

    private void init() {
        Button btnCall = (Button)findViewById(R.id.btnRetrofit);
        btnCall.setText("Retrofit");

        Button btnBasic = (Button)findViewById(R.id.btnBasic);
        btnBasic.setText("Basic");

        txtNetworkContext = (TextView)findViewById(R.id.txtNetworkContext);
    }

    public void mClick(View v){
        switch (v.getId()) {
            case R.id.btnRetrofit:
                mProgress = ProgressDialog.show(this,"wait","Downloading....");
                Toast.makeText(this, "rtro", Toast.LENGTH_SHORT).show();
                mRaw = true;
                RtroThread rtroThread = new RtroThread(API_URL);
                rtroThread.start();
                break;
            case R.id.btnBasic:
                mProgress = ProgressDialog.show(this,"wait","Downloading....");
                Toast.makeText(this, "basic webservice", Toast.LENGTH_SHORT).show();
                mRaw = true;
                DownThread thread = new DownThread(API_URL);
                thread.start();
                break;
        }

    }
//
//    private void parseJsonData(String data){
//
//        String jsonData="";
//        try{
//            int sum = 0;
//            JSONArray jsonArray = new JSONArray(data);
//            for(int i=0; i<jsonArray.length(); i++){
//                JSONObject object = jsonArray.getJSONObject(i);
//                jsonData = object.toString();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        txtNetworkContext.setText(jsonData);
//    }

    Handler mAfterDown = new Handler(){
        public void handleMessage(Message msg){
            Log.d(TAG, ">> handleMessage : " + msg);
            mProgress.dismiss();
            String resultData = (String)msg.obj;
            Log.d(TAG,">> resultData : "+ resultData);

            if(!mRaw){
                String Result = "";
                try{

                }catch (Exception e){

                }
            }
            txtNetworkContext.setText(resultData);
        }

    };

    private class DownThread extends Thread {
        String mAddr;

        DownThread(String addr) {
            mAddr = addr;
        }

        public void run() {
            String result = DownloadJson(API_URL);

            mAfterDown.obtainMessage();
            message.obj = result;
            mAfterDown.sendMessage(message);
        }

        String DownloadJson(String addr) {
            Log.d(TAG, ">> DownloadJson");
            StringBuilder JsonData = new StringBuilder();
            try {
                URL url = new URL(API_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(
                                new InputStreamReader(conn.getInputStream()));
                        for (;;) {
                            String line = br.readLine();
                            if (line == null) break;
                            JsonData.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception e) {
                return "ERROR : " + e.getMessage();
//            } catch (NetworkOnMainThreadException e) {
//                    return "ERROR : MAIN THREAD NETWORK WORKING PROBLEM" + e.getMessage();
            }
            Log.d(TAG, ">>" + JsonData.toString());
            return JsonData.toString();
        }
    }

    private class RtroThread extends Thread{
        String mUrl;
        public RtroThread(String apiUrl) {
            mUrl = apiUrl;
        }

        public void run() {
            List<Contributor> result = null;

            Log.d(TAG,">> RtroThread run : "+result);
//            String result = "result??";
            try {
//                result = RetroDownloadJson(mUrl);
                result = RetroDownloadJson(mUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(TAG, ">> RtroThread run result: " + result);
        }
    }

    private List<Contributor> RetroDownloadJson(String mUrl) throws Exception{
        Log.d(TAG,">> RetroDownloadJson(String "+mUrl+")");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        Log.d(TAG,">> retrofit build");
        // Create an instance of our GitHub API interface.
        GitHub github = retrofit.create(GitHub.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Contributor>> call = github.contributors("square", "retrofit");
        // Fetch and print a list of the contributors76 to the library.
        // contributors = call.execute().body();//동기

        Log.d(TAG, ">> call.enqueue");
        call.enqueue(new Callback<List<Contributor>>() {
            String result = ">> result : ";

            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {

                if (!response.isSuccessful()) {
                    result += "error response, no access to resource?";
                    System.out.println(call.request().url() + ": failed: " + response.code());
                    return;
                } else {
                    // tasks available
                    result += " tasks available : " + response.message() + " \n"
                            + "raw : " + response.raw() + " \n"
                            + "body : " + response.body().toArray();
                    parseJSONData(response.body());
                }
                Log.d(TAG, ">> onResponse : " + result);

                message = mAfterDown.obtainMessage();
                message.obj = result;
                mAfterDown.sendMessage(message);
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                // something went completely south (like no internet connection)
                result += t.getMessage();
                Log.d(TAG, ">> onFailure : " + result);
//                responseData.add((Contributor) result);
            }
        });

        return ContributorData;
    }

    private void parseJSONData(List<Contributor> response) {
        ContributorData = new ArrayList<>();

        Log.d(TAG, ">> parseJSONData");

        for (Contributor contributor : response) {
            System.out.println(">> " + contributor.login + " (" + contributor.contributions + ")");
            ContributorData.add(contributor);
        }

    }
}
