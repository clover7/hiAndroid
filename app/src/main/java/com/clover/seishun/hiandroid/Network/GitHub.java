package com.clover.seishun.hiandroid.Network;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by heaun.b on 2016. 4. 3..
 */
public interface GitHub {
    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo);

    @GET("/omni/level/")
    Call<HashMap<String, Object>> youngInit(
        @Header("svcAuthKey") String svcAuthKey,
        @Header("mbrId") String mbrId);

    @POST("omni/exer/outCalo/strength/muscle")
    Call<HashMap<String, Object>> insertMuscleStrength(
            @Header("svcAuthKey") String svcAuthKey,
            @Header("mbrId") String mbrId,
            @Body List<HashMap<String, String>> params);

    //Post 구현 해보기

}
