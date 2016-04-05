package com.swoa.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.clover.seishun.hiandroid.R;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SWOAWebserviceActivity extends AppCompatActivity {
    private static final String TAG = SWOAWebserviceActivity.class.getSimpleName();
    private static final String API_URL = "http://129.254.221.27:8080";
    private String result;

    DeviceInfo deviceInfo;
    ProgressDialog mProgress;
//    @ViewById(R.id.txtLoginUserInfo)
//    TextView loginUserInfo;

//    @ViewById(R.id.userId)
//    TextView userId;
//
//    @ViewById(R.id.password)
//    TextView password;

    private String userId;
    private int password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swoa_webservice);

        Intent intent = getIntent();
        HashMap<String, String> result = (HashMap<String, String>) intent.getSerializableExtra("loginUser");

        TextView txtUserId = (TextView)findViewById(R.id.userId);
        TextView txtPassword = (TextView)findViewById(R.id.password);

        txtUserId.setText("userId : ");
        txtPassword.setText("password : ");

        userId = result.get("userId");
        String strPassword = result.get("password");
        password = Integer.parseInt(strPassword);
        txtUserId.append(userId);
        txtPassword.append(String.valueOf(password));
        //retrofitStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProgress = ProgressDialog.show(this,"wait","Downloading....");
        getLoginUserInfo(userId, password);
    }

    private void getLoginUserInfo(String userId, Integer password) {
        Log.d(TAG, ">> RetroDownloadJson(String " + API_URL + ")");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        Log.d(TAG, ">> retrofit build");

        HashMap<String, Object> params = new HashMap<>();
        params.put("userID", userId);
        params.put("password", password);

        SWOAWebservice swoaWebservice = retrofit.create(SWOAWebservice.class);

        Call<UserInfo> loginUserCall = swoaWebservice.loginUserMap(params);
        loginUserCall.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {

                result = "\n ========= Response ========= \n";
                result += ">> message : " + response.message() + " \n"
                        + ">> response : " + response.raw() + " \n"
                        + ">> body : " + response.body();
                if (!response.isSuccessful()) {
                    result += ">> error response, no access to resource?";
                    System.out.println(call.request().url() + ": failed: " + response.code());
                    return;
                } else {
                    // tasks available
                    parseJSONData(response.body());
                }
                Log.d(TAG, ">> onResponse : " + result);
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                result = "\n ========= Failure ========= \n";
                result += ">> Throwable message  : " + t.getMessage() + " \n";

                Log.d(TAG, ">> onFailure : " + result);
            }
        });
        mProgress.dismiss();
    }


    private void retrofitStart() {
        Log.d(TAG, ">> RetroDownloadJson(String " + API_URL + ")");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        Log.d(TAG,">> retrofit build");

        // Create an instance of our GitHub API interface.
        SWOAWebservice swoaWebservice = retrofit.create(SWOAWebservice.class);

        Call<DeviceInfo> deviceInfoCall = swoaWebservice.createUser(deviceInfo);

        Call<DeviceInfo> loginUserCall = swoaWebservice.loginUser("INswoapat1", 1234);
        loginUserCall.enqueue(new Callback<DeviceInfo>() {
            @Override
            public void onResponse(Call<DeviceInfo> call, Response<DeviceInfo> response) {
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
            }

            @Override
            public void onFailure(Call<DeviceInfo> call, Throwable t) {
                result += t.getMessage();
                Log.d(TAG, ">> onFailure : " + result);
            }
        });
    }

    private void parseJSONData(DeviceInfo body) {
        Log.d(TAG, ">> parseJSONData : String");

        System.out.println(">> " + body);
//        userId.append();
//        password.append();

        TextView loginUserInfo = (TextView)findViewById(R.id.txtLoginUserInfo);
        loginUserInfo.setText(body.toString());
    }

    private void parseJSONData(UserInfo body) {
        Log.d(TAG, ">> parseJSONData : String");

        System.out.println(">> " + body);
//        userId.append();
//        password.append();

        TextView loginUserInfo = (TextView)findViewById(R.id.txtLoginUserInfo);
        result += "\n\n ========= Result ========= \n";
                result += body.getStatusProperty() + "\n";
        result += body.getBasicProperty() + "\n";
        result += body.getAdditionalProperty() + "\n";
        result += body.getServiceIDListProperty() + "\n";
        result += "UpdatedDate : "+ body.getUpdatedDate() + "\n";
        result += "CreatedDate : " + body.getCreatedDate() + "\n";
        result += "ID : " + body.getId() + "\n";

        loginUserInfo.setText(result);
    }

}
