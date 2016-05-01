package com.swoa.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.clover.seishun.hiandroid.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.swoa.test.pojo.UserProperties;
import com.swoa.test.pojo.User;
import com.swoa.test.pojo.user.AdditionalProperty;
import com.swoa.test.pojo.user.Address;
import com.swoa.test.pojo.user.BasicProperty;
import com.swoa.test.pojo.user.PhoneNumber;
import com.swoa.test.pojo.user.StatusProperty;
import com.swoa.test.pojo.user.UserName;

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
    private String firstName;
    private String lastName;
    private String sex;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    SWOAWebservice swoaWebservice = retrofit.create(SWOAWebservice.class);

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
        firstName = params.get("firstName");
        lastName  = params.get("lastName");
        sex = "FEMALE";

        password = Integer.parseInt(strPassword);
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

    private void setUserInfo() {
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

        UserProperties userProperties = new UserProperties();
        BasicProperty basicProperty = new BasicProperty();
        StatusProperty statusProperty = new StatusProperty();
        AdditionalProperty additionalProperty = new AdditionalProperty();

        basicProperty.setUserID(userId);
        basicProperty.setUserPW(strPassword);
        basicProperty.setSex(sex);
        basicProperty.setUserName(null);
        basicProperty.setRoles(null);
        basicProperty.setSecurityLevel(null);
        basicProperty.setPrivacyLevel(null);
        basicProperty.setLastLogin(null);
        basicProperty.setSsn(null);
        basicProperty.setMarriage(null);

        PhoneNumber phoneNumber = new PhoneNumber();
        basicProperty.setPhoneNumber(phoneNumber);
        Address address = new Address();
        address.setApt(null);
        basicProperty.setAddress(address);

        UserName userName = new UserName();
        userName.setFirstName(firstName);
        userName.setLastName(lastName);
        basicProperty.setUserName(userName);

        userProperties.setAdditionalProperty(additionalProperty);
        userProperties.setBasicProperty(basicProperty);
        userProperties.setStatusProperty(statusProperty);

        Call<User> createUserCall = service.createUser(userProperties);
        createUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
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
            public void onFailure(Call<User> call, Throwable t) {
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
        Log.d(TAG, ">> retrofit build");

        HashMap<String, Object> params = new HashMap<>();
        params.put("userID", userId);
        params.put("password", password);



        Call<User> loginUserCall = swoaWebservice.loginUserMap(params);
        loginUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

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
            public void onFailure(Call<User> call, Throwable t) {
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

        switch(event) {
            case 1:
                mProgress = ProgressDialog.show(this,"wait","Downloading....");
                setUserInfo();
                mProgress.dismiss();
                break;
            case 2:
                mProgress = ProgressDialog.show(this,"wait","Downloading....");
                getLoginUserInfo(userId, password);
                mProgress.dismiss();
                break;
        }
    }

    public void getUser(User params){
        Call<User> findUserCall = swoaWebservice.findUser(params);
        findUserCall.enqueue(new Callback<User>(){
            //params : ((ExecutorCallAdapterFactory.ExecutorCallbackCall) call).delegate.args

            String rResult = "";
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
//                responseInfo(call, response);

                if (!response.isSuccessful()) {
                    result += ">> error responseInfo, no access to resource?";
                    result += call.request().url() + ": failed: " + response.code();
                    System.out.println(call.request().url() + ": failed: " + response.code());

                    return;
                } else {
                    // tasks available
//                    result += ">> responseInfo : " + response.raw() + " \n"
//                            + ">> body : " + response.body();

                    User body = response.body();
                    String msg = body.getMsg();
                    int code = body.getCode();

                    rResult= "========== Result =============\n";
                    if(msg == null){
                        rResult += "ID : "  + body.getId()+ "\n";
                        rResult += "CREATE DATE : " + body.getCreatedDate()+ "\n";
                        rResult += "UPDATE DATE : " + body.getUpdatedDate()+ "\n";
                        rResult += "STATUS : " + body.getStatusProperty().getStatus()+ "\n";
                        rResult += "UserID : "  + body.getBasicProperty().getUserID()+ "\n";
                        rResult += "UserPW : "  + body.getBasicProperty().getUserPW()+ "\n";
                        rResult += "SEX : "     + body.getBasicProperty().getSex()+ "\n";
                        rResult += "NAME : "    + body.getBasicProperty().getUserName().getFirstName() +" "
                                + body.getBasicProperty().getUserName().getMiddleName() +" "
                                + body.getBasicProperty().getUserName().getLastName()+ "\n";
                    }
                }
//                showToastMessage(rResult);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                result = "\n ========= Failure ========= \n";
                result += ">> Throwable message  : " + t.getMessage() + " \n";

                Log.d(TAG, ">> onFailure : " + result);
                TextView loginUserInfo = (TextView)findViewById(R.id.txtLoginUserInfo);
                loginUserInfo.setText(result);
            }
        });
    }


    private void parseJSONData(User body) {
        Log.d(TAG, ">> parseJSONData : String");

        System.out.println(">> " + body);

        String msg = body.getMsg();
        int code = body.getCode();

        String result= "========== Result =============\n";
        if(msg == null){
            result += "UserID : "  + body.getBasicProperty().getUserID()+ "\n";
            result += "UserPW : "  + body.getBasicProperty().getUserPW()+ "\n";
            result += "Sex : "     + body.getBasicProperty().getSex()+ "\n";
            result += "NAME : "    + body.getBasicProperty().getUserName().getFirstName() +" "
                                    + body.getBasicProperty().getUserName().getMiddleName() +" "
                                    + body.getBasicProperty().getUserName().getLastName()+ "\n";
        }else{
            result += "msg : " + msg + "\n";
            result += "code : "+ code+ "\n";
        }
        TextView loginUserInfo = (TextView)findViewById(R.id.txtLoginUserInfo);
        loginUserInfo.setText(result);
    }

//    private void parseJSONData(User body) {
//        Log.d(TAG, ">> parseJSONData : String");
//
//        System.out.println(">> " + body);
////        userId.append();
////        password.append();
//
//        TextView loginUserInfo = (TextView)findViewById(R.id.txtLoginUserInfo);
//        result += "\n\n ========= Result ========= \n";
//                result += body.getStatusPropertyUser() + "\n";
//        result += body.getBasicPropertyUser() + "\n";
//        result += body.getAdditionalPropertyUser() + "\n";
//        result += body.getServiceIDListProperty() + "\n";
//        result += "UpdatedDate : "+ body.getUpdatedDate() + "\n";
//        result += "CreatedDate : " + body.getCreatedDate() + "\n";
//        result += "ID : " + body.getId() + "\n";
//
//        loginUserInfo.setText(result);
//    }

}
