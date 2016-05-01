package com.swoa.test;

import com.swoa.test.pojo.User;
import com.swoa.test.pojo.UserProperties;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by heaun.b on 2016. 4. 5..
 */
public interface SWOAWebservice {


        /**
         * 사용자 조회 (디바이스)
         http://129.254.221.27:8080/swomanager/users/{userID} GET
         {"code" : 404, "msg" : "Not found"}
         */

        @GET("/swomanager/users/{userID}")
        Call<User> findUser(@Path("userID") String userID);


    @Headers({
        "Cache-Control: no-cache",
        "Accept: */*",
        "Accept-Encoding: gzip, deflate",
        "Accept-Language: ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4",
        "Cookie: JSESSIONID=286DA79D26E1C5F8F3F756C0D7C452DE"
})
    @POST("/swomanager/users")
    Call<User> createUser(@Body UserProperties params);
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
    Call<UserProperties> loginUser(
            @Query("userID") String userID,
            @Query("password") int password);

    @GET("swomanager/users/login")
    Call<User> loginUserMap(
            @QueryMap HashMap<String, Object> params);


    //
//    사용자 로그인 (서비스)
//    http://129.254.221.27:8080/swomanager/users/login GET

    //    http://129.254.221.27:8080/swomanager/users/login?userID=INswoapat1&password=1234
    @GET("/swomanager/users/login")
    Call<User> loginUser(
            @Query("userID") String userID,
            @Query("password") int password);
    //
//    홈 생성 (서비스)
//    http://129.254.221.27:8080/swomanager/homes POST
//
    @POST("swomanager/homes")
    Call<Home> registerHomes(@Body Home params);

    //    룸 생성 (생성과 동시에 룸을 홈에 등록)
//    http://129.254.221.27:8080/swomanager/rooms POST
//
    @POST("swomanager/rooms")
    Call<Home> registerRooms(@Body Home params);

    //    디바이스를 룸에 등록 (동시에 홈 등록과 활성화)
//    http://129.254.221.27:8080/swomanager/devices/serial/{serialNo}/home/{homeId}/room/{roomId} PUT
    @PUT("swomanager/devices/serial/{serialNo}/home/{homeId}/room/{roomId}")
    Call<String> registerDevice(
            @Path("serialNo") int serialNo,
            @Path("homeId") String homeId,
            @Path("roomId") String roomId
    );

    //    EVENT 확인 (서비스)
    //    http://129.254.221.27:8080/swomanager/events/devices/{deviceId} GET
    @GET("swomanager/events/devices/{deviceId}")
    Call<Event> getEventList(
            @Path("deviceId") String deviceId);
}


//    사용자 등록 (디바이스)
//    http://129.254.221.27:8080/swomanager/users POST
//
//    사용자 로그인 (디바이스)
//    http://129.254.221.27:8080/swomanager/users/login GET
//
// SPEC 전송 (디바이스)
//    http://129.254.221.27:8080/swona/spec POST
//

    //-----------------------------------------------------
//
//    사용자 로그인 (서비스)
//    http://129.254.221.27:8080/swomanager/users/login GET
//
//    홈 생성 (서비스)
//    http://129.254.221.27:8080/swomanager/homes POST
//
//    룸 생성 (생성과 동시에 룸을 홈에 등록)
//    http://129.254.221.27:8080/swomanager/rooms POST
//
//    디바이스를 룸에 등록 (동시에 홈 등록과 활성화)
//    http://129.254.221.27:8080/swomanager/devices/serial/{serialNo}/home/{homeId}/room/{roomId} PUT
//
//    EVENT 전송 (디바이스)
//    http://129.254.221.27:8080/swona/event POST
//
//    EVENT 확인 (서비스)
//    http://129.254.221.27:8080/swomanager/events/devices/{deviceId} GET

//            > 위 API의 테스트는 http://129.254.221.27:8080/swona와 http://129.254.221.27:8080/swomanager에서 수행 가능



}
