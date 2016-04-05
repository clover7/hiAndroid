package com.clover.seishun.hiandroid.Network;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
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



    //Post 구현 해보기

}
