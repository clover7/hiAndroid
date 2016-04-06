package com.swoa.test;

import com.swoa.test.pojo.ResultInfo;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by heaun.b on 2016. 4. 5..
 */
public interface SWOAWebservice {
    @Headers({
        "Cache-Control: no-cache",
        "Accept: */*",
        "Accept-Encoding: gzip, deflate",
        "Accept-Language: ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4",
        "Cookie: JSESSIONID=286DA79D26E1C5F8F3F756C0D7C452DE"
})
    @POST("/swomanager/users")
    Call<ResultInfo> createUser(@Body DeviceInfo params);
//    POST /swomanager/users HTTP/1.1
    //                Host: 129.254.221.27:8080
//                Connection: keep-alive
//                Connection: keep-alive
//                Content-Length: 762
//                Cache-Control: no-cache
//                Origin: chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop
//                Content-Type: application/json
//
//                User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36
//                Postman-Token: eb156c6d-afed-20ab-0c6d-a7e7b739bc51
//
//                Accept: */*
//    "Accept-Encoding: gzip, deflate",
//            "Accept-Language: ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4",
//            "Cookie: JSESSIONID=286DA79D26E1C5F8F3F756C0D7C452DE"


//    Call<HashMap<String, Objects>> createUser( @Body String params);

//    http://129.254.221.27:8080/swomanager/users/login?userID=INswoapat1&password=1234
    @GET("swomanager/users/logins")
    Call<DeviceInfo> loginUser(
            @Query("userID") String userID,
            @Query("password") int password);

    @GET("swomanager/users/login")
    Call<UserInfo> loginUserMap(
            @QueryMap HashMap<String, Object> params);
}
