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

import com.clover.seishun.hiandroid.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

//import org.apache.log4j.Logger;


public class RetrofitLibraryActivity extends AppCompatActivity {

    private static final String GIT_API_URL = "https://api.github.com";
    private static final String API_URL = "http://youth.dhc.or.kr/";


    private static final String TAG = RetrofitLibraryActivity.class.getSimpleName();


    private static final String MBRID = "alicepark5";
    private static final String SVC_AUTH_KEY = "kthealth_youthHealth_key_8d5bbbaccd472b45ae02beb1bd201308";

    TextView txtNetworkContext;
    boolean mRaw;
    ProgressDialog mProgress;
    List<Contributor> responseData = null;
    List<Contributor> ContributorData;
    Message message;

    String result = ">> result : ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtrofit_library);
        init();
    }

    private void init() {
        Button btnCall = (Button)findViewById(R.id.btnRetrofit);
        btnCall.setText("Retrofit");

        Button btnBasic = (Button)findViewById(R.id.btnBasic);
        btnBasic.setText("Basic");

        Button btnBasicToRetrofit = (Button)findViewById(R.id.w_Btn3);
        btnBasicToRetrofit.setText("BasicToRetrofit");

        Button btnAsyncTask = (Button)findViewById(R.id.btnAsyncTask);
        btnAsyncTask.setText("AsyncTask");

        Button btnVolley = (Button)findViewById(R.id.btnVolley);
        btnVolley.setText("Volley");

        Button btnPostTest = (Button)findViewById(R.id.btnPostTest);
        btnPostTest.setText("PostTest");



        txtNetworkContext = (TextView)findViewById(R.id.txtNetworkContext);
    }

    public void mClick(View v){
        switch (v.getId()) {
            case R.id.btnRetrofit:
                mProgress = ProgressDialog.show(this,"wait","Downloading....");
                mRaw = true;
                retrofitStart();
                break;
            case R.id.btnBasic:
                mProgress = ProgressDialog.show(this,"wait","Downloading....");
                mRaw = true;
                DownThread thread = new DownThread(API_URL);
                thread.start();
                break;
            case R.id.w_Btn3:
                mProgress = ProgressDialog.show(this,"wait","Downloading....");
                mRaw = true;
                startBasicToRetrofit();
                break;
            case R.id.btnAsyncTask:
                mProgress = ProgressDialog.show(this,"wait","Downloading....");
                mRaw = true;
                startAsyncTask();
                break;
            case R.id.btnPostTest:
                mProgress = ProgressDialog.show(this,"wait","Downloading....");
                mRaw = true;
                startPostTest();
                break;
        }
    }

    private void startPostTest() {
        Log.d(TAG, ">> RetroDownloadJson(String " + API_URL + ")");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        Log.d(TAG,">> retrofit build");

        // Create an instance of our GitHub API interface.
        GitHub github = retrofit.create(GitHub.class);

        List<HashMap<String, String>> paramArray = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();

        params.put("muscleExerCd", "F040");
        paramArray.add(params);
        params.put("muscleExerCd", "F055");
        paramArray.add(params);
        params.put("muscleExerCd", "F020");
        paramArray.add(params);
        params.put("muscleExerCd", "F010");
        paramArray.add(params);

        Call<HashMap<String,Object>> callMuscleStrength = github.insertMuscleStrength(SVC_AUTH_KEY, MBRID, paramArray);
        callMuscleStrength.enqueue(new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {

                if (!response.isSuccessful()) {
                    result += "error response, no access to resource?";
                    System.out.println(call.request().url() + ": failed: " + response.code());
                    return;
                } else {
                    // tasks available
                    result += "tasks available : " + response.message() + " \n"
                            + "raw : " + response.raw() + " \n"
                            + "body : " + response.body();
                    parseJSONData(response.body());
                }
                Log.d(TAG, ">> onResponse : " + result);
                mProgress.dismiss();
            }

            @Override
            public void onFailure(Call<HashMap<String, Object>> call, Throwable t) {
                result += t.getMessage();
                Log.d(TAG, ">> onFailure : " + result);
            }
        });
    }

    private void startBasicToRetrofit() {
        Log.d(TAG, ">> RetroDownloadJson(String " + GIT_API_URL + ")");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GIT_API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        Log.d(TAG,">> retrofit build");

        // Create an instance of our GitHub API interface.
        GitHub github = retrofit.create(GitHub.class);

        Call<List<Contributor>> call = github.contributors("square", "retrofit");
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                if (!response.isSuccessful()) {
                    result += "error response, no access to resource?";
                    System.out.println(call.request().url() + ": failed: " + response.code());
                    return;
                } else {
                    // tasks available
                    result += "tasks available : " + response.message() + " \n"
                            + "raw : " + response.raw() + " \n"
                            + "body : " + response.body();
                    parseJSONData(response.body());
                }
                Log.d(TAG, ">> onResponse : " + result);
                mProgress.dismiss();
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                result += t.getMessage();
                Log.d(TAG, ">> onFailure : " + result);
            }
        });

    }

    private void retrofitStart() {
        Log.d(TAG, ">> RetroDownloadJson(String " + API_URL + ")");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        Log.d(TAG,">> retrofit build");

        // Create an instance of our GitHub API interface.
        GitHub github = retrofit.create(GitHub.class);

        Call<HashMap<String,Object>> callyoungInit = github.youngInit(SVC_AUTH_KEY, MBRID);
        callyoungInit.enqueue(new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {

                if (!response.isSuccessful()) {
                    result += "error response, no access to resource?";
                    System.out.println(call.request().url() + ": failed: " + response.code());
                    return;
                } else {
                    // tasks available
                    result += "tasks available : " + response.message() + " \n"
                            + "raw : " + response.raw() + " \n"
                            + "body : " + response.body();
                    parseJSONData(response.body());
                }
                Log.d(TAG, ">> onResponse : " + result);
                mProgress.dismiss();
            }

            @Override
            public void onFailure(Call<HashMap<String, Object>> call, Throwable t) {
                result += t.getMessage();
                Log.d(TAG, ">> onFailure : " + result);
            }
        });
    }

    private void parseJSONData(HashMap<String, Object> body) {
        Log.d(TAG, ">> parseJSONData : String");

        System.out.println(">> " + body);
        txtNetworkContext.setText(body.toString());
    }

    private void parseJSONData(List<Contributor> response) {
        ContributorData = new ArrayList<>();

        Log.d(TAG, ">> parseJSONData");

        String data="";
        for (Contributor contributor : response) {
            String result = contributor.login + " (" + contributor.contributions + ")";
            data += result;
            System.out.println(">> " + result);
            ContributorData.add(contributor);
            txtNetworkContext.setText(result);
        }

        txtNetworkContext.setText(data);
    }


    /** AsyncTask() 사용 */

    private void startAsyncTask() {
    }

    /**thread & handler 사용*/

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
            String result = DownloadJson(GIT_API_URL);

            Log.d(TAG, ">> run RESULT :"+ result);

            message = mAfterDown.obtainMessage();
            message.obj = result;
            mAfterDown.sendMessage(message);
        }

        String DownloadJson(String addr) {
            Log.d(TAG, ">> DownloadJson addr :"+ addr);
            StringBuilder JsonData = new StringBuilder();
            try {
                URL url = new URL(addr);
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
} 

