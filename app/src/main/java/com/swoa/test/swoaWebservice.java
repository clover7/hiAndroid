package com.swoa.test;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by heaun.b on 2016. 4. 5..
 */
public interface SWOAWebservice {
    @POST("/swoamanager/users")
    Call<DeviceInfo> createUser(@Body DeviceInfo deviceInfo);

    @POST("/swoamanager/users")
    Call<DeviceInfo> postTest(@Body DeviceInfo deviceInfo);

//    http://129.254.221.27:8080/swomanager/users/login?userID=INswoapat1&password=1234
    @GET("swomanager/users/logins")
    Call<DeviceInfo> loginUser(
            @Query("userID") String userID,
            @Query("password") int password);

    @GET("swomanager/users/login")
    Call<UserInfo> loginUserMap(
            @QueryMap HashMap<String, Object> params);
}
