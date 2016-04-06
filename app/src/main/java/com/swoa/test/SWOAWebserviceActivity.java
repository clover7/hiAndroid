package com.swoa.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.clover.seishun.hiandroid.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.swoa.test.pojo.AdditionalProperty;
import com.swoa.test.pojo.Address;
import com.swoa.test.pojo.BasicProperty;
import com.swoa.test.pojo.PhoneNumber;
import com.swoa.test.pojo.ResultInfo;
import com.swoa.test.pojo.StatusProperty;
import com.swoa.test.pojo.UserName;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SWOAWebserviceActivity extends AppCompatActivity {
    private static final String TAG = SWOAWebserviceActivity.class.getSimpleName();
    private static final String API_URL = "http://129.254.221.27:8080";
    private String result = null;

    DeviceInfo deviceInfo;
    ProgressDialog mProgress;
//    @ViewById(R.id.txtLoginUserInfo)
//    TextView loginUserInfo;

//    @ViewById(R.id.userId)
//    TextView userId;
//
//    @ViewById(R.id.password)
//    TextView password;

    private String userId="";
    private int password;
    private String strPassword="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swoa_webservice);

        Intent intent = getIntent();
        HashMap<String, String> params = (HashMap<String, String>) intent.getSerializableExtra("params");

        TextView txtUserId = (TextView)findViewById(R.id.userId);
        TextView txtPassword = (TextView)findViewById(R.id.password);

        txtUserId.setText("userId : ");
        txtPassword.setText("password : ");

        userId = params.get("userId");
        strPassword = params.get("password");
        password = Integer.parseInt(strPassword);

//        Serializable event = intent.getSerializableExtra("btnClick");
        int event = intent.getIntExtra("btnClick", 0);
        try {
            retrofitStart(event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        txtUserId.append(userId);
        txtPassword.append(String.valueOf(password));
        //retrofitStart();
    }

    private void setUserInfo(SWOAWebservice swoaWebservice) throws JsonProcessingException {
        Log.d(TAG, ">> RetroDownloadJson(String " + API_URL + ")");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        Log.d(TAG, ">> retrofit build");

        HashMap<String, Object> params = new HashMap<>();
        params.put("userID", userId);
        params.put("password", password);

        SWOAWebservice service = retrofit.create(SWOAWebservice.class);

        DeviceInfo deviceInfo = new DeviceInfo();
        BasicProperty basicProperty = new BasicProperty();
        StatusProperty statusProperty = new StatusProperty();
        AdditionalProperty additionalProperty = new AdditionalProperty();

        basicProperty.setUserID(userId);
        basicProperty.setUserPW(strPassword);
        basicProperty.setSex("FEMALE");

        PhoneNumber phoneNumber = new PhoneNumber();
        basicProperty.setPhoneNumber(phoneNumber);

        Address address = new Address();
        basicProperty.setAddress(address);

        UserName userName = new UserName();
        userName.setFirstName("test1");
        userName.setLastName("pat1");
        basicProperty.setUserName(userName);

        deviceInfo.setAdditionalProperty(additionalProperty);
        deviceInfo.setBasicProperty(basicProperty);
        deviceInfo.setStatusProperty(statusProperty);

        Call<ResultInfo> createUserCall = service.createUser(deviceInfo);
        createUserCall.enqueue(new Callback<ResultInfo>() {
            @Override
            public void onResponse(Call<ResultInfo> call, Response<ResultInfo> response) {
                result = "\n ========= Response ========= \n";

                if (!response.isSuccessful()) {
                    result +=">> request header: " + response.raw().request().headers()+ " \n";
                    result +=">> response header: " + response.headers()+ " \n";
                    result +=">> response message: " + response.message()+ " \n";

                    System.out.println(call.request().url() + ": failed: " + response.code());
                    System.out.println(">> request header: " + response.raw().request().headers());
                    System.out.println(">> response header: " + response.headers());
                    System.out.println(">> response message: " + response.message());
                    TextView loginUserInfo = (TextView)findViewById(R.id.txtLoginUserInfo);
                    loginUserInfo.setText(result);
                    return;
                } else {
                    // tasks available
                    result += ">> response : " + response.raw() + " \n"
                            + ">> body : " + response.body();

                    parseJSONData(response.body());
                }
                Log.d(TAG, ">> onResponse : " + result);
            }

            @Override
            public void onFailure(Call<ResultInfo> call, Throwable t) {
                result = "\n ========= Failure ========= \n";
                result += ">> Throwable message  : " + t.getMessage() + " \n";
                Log.d(TAG, ">> onFailure : " + result);
                t.printStackTrace();
            }
        });
    }

    @Override
        protected void onResume() {
        super.onResume();
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
                    result += call.request().url() + ": failed: " + response.code();
                    System.out.println(call.request().url() + ": failed: " + response.code());
                    TextView loginUserInfo = (TextView)findViewById(R.id.txtLoginUserInfo);
                    loginUserInfo.setText(result);
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
                TextView loginUserInfo = (TextView)findViewById(R.id.txtLoginUserInfo);
                loginUserInfo.setText(result);
            }
        });
        mProgress.dismiss();
    }

    private void retrofitStart(int event) throws JsonProcessingException {
        Log.d(TAG, ">> RetroDownloadJson(String " + API_URL + ")");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        Log.d(TAG,">> retrofit build");

        // Create an instance of our GitHub API interface.
        SWOAWebservice swoaWebservice = retrofit.create(SWOAWebservice.class);

        switch(event) {
            case 1:
                mProgress = ProgressDialog.show(this,"wait","Downloading....");
                setUserInfo(swoaWebservice);
                mProgress.dismiss();
                break;
            case 2:
                mProgress = ProgressDialog.show(this,"wait","Downloading....");
                getLoginUserInfo(userId, password);
                mProgress.dismiss();
                break;
        }
    }

    private void parseJSONData(DeviceInfo body) {
        Log.d(TAG, ">> parseJSONData : String");

        System.out.println(">> " + body);
//        userId.append();
//        password.append();

//        HashMap<String,Object> data = (HashMap<String, Object>) body;
//        int resultCode = (int) data.get("code");
//        String rereltMessage = (String) data.get("msg");
//
//
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
                result += body.getStatusPropertyUser() + "\n";
        result += body.getBasicPropertyUser() + "\n";
        result += body.getAdditionalPropertyUser() + "\n";
        result += body.getServiceIDListProperty() + "\n";
        result += "UpdatedDate : "+ body.getUpdatedDate() + "\n";
        result += "CreatedDate : " + body.getCreatedDate() + "\n";
        result += "ID : " + body.getId() + "\n";

        loginUserInfo.setText(result);
    }

}
