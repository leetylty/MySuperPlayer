package com.example.administrator.mysuperplayer.util;

import com.example.administrator.mysuperplayer.AppManager;

import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/8/22.
 */

public class OkHttpUtil {
    public static  final  String TAG="okhttp";

    public static Request buildRequest(String url){

        if (AppManager.isNetworkEnable()){
            Request  request =  new Request.Builder().tag(TAG)
                    .url(url)
                    .build();
            return request;
        }
        return null;
    }


    public static void excute (String url , Callback callback){
         Request request = buildRequest(url);
         excute(request,callback);
    }

    public static void excute(Request request,Callback callback){
         AppManager.getmOkHttpClient().newCall(request).enqueue(callback);
    }
}
