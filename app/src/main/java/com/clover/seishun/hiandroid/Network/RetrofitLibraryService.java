package com.clover.seishun.hiandroid.Network;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class RetrofitLibraryService extends Service {
    public RetrofitLibraryService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
//
//    List<Contributor> ContributorData;
//
//    private List<Contributor> RetroDownloadJson(String mUrl) throws Exception{
//        Log.d(TAG, ">> RetroDownloadJson(String " + mUrl + ")");
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(mUrl)
//                .addConverterFactory(JacksonConverterFactory.create())
//                .build();
//
//        Log.d(TAG,">> retrofit build");
//        // Create an instance of our GitHub API interface.
//        GitHub github = retrofit.create(GitHub.class);
//
//        // Create a call instance for looking up Retrofit contributors.
//        Call<List<Contributor>> call = github.contributors("square", "retrofit");
//        // Fetch and print a list of the contributors76 to the library.
//        // contributors = call.execute().body();//동기
//
//        Log.d(TAG, ">> call.enqueue");
//        call.enqueue(new Callback<List<Contributor>>() {
//            String result = ">> result : ";
//
//            @Override
//            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
//
//                if (!response.isSuccessful()) {
//                    result += "error response, no access to resource?";
//                    System.out.println(call.request().url() + ": failed: " + response.code());
//                    return;
//                } else {
//                    // tasks available
//                    result += " tasks available : " + response.message() + " \n"
//                            + "raw : " + response.raw() + " \n"
//                            + "body : " + response.body().toArray();
//                    parseJSONData(response.body());
//                }
//                Log.d(TAG, ">> onResponse : " + result);
//
//                message = mAfterDown.obtainMessage();
//                message.obj = result;
//                mAfterDown.sendMessage(message);
//            }
//
//            @Override
//            public void onFailure(Call<List<Contributor>> call, Throwable t) {
//                // something went completely south (like no internet connection)
//                result += t.getMessage();
//                Log.d(TAG, ">> onFailure : " + result);
////                responseData.add((Contributor) result);
//            }
//        });
//
//        return ContributorData;
//    }
//    private void parseJSONData(List<Contributor> response) {
//        ContributorData = new ArrayList<>();
//
//        Log.d(TAG, ">> parseJSONData");
//
//        for (Contributor contributor : response) {
//            System.out.println(">> " + contributor.login + " (" + contributor.contributions + ")");
//            ContributorData.add(contributor);
//        }
//
//    }

}
