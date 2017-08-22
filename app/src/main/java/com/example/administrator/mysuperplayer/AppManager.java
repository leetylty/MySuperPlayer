package com.example.administrator.mysuperplayer;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;

/**
 * Created by admin on 2017/8/21.
 */

public class AppManager extends Application {

     private static Gson mGosn;
     private static OkHttpClient mOkHttpClient;
     private  static Context mContext;

     @Override
     public void onCreate() {
          super.onCreate();
          mContext =this;
          mGosn = new Gson();
          mOkHttpClient = new OkHttpClient();
     }

     public static Gson getGson(){

          return  mGosn;
     }
     public static OkHttpClient getmOkHttpClient(){

          return  mOkHttpClient;
     }
     public static Context getcontext(){
          return mContext;
     }
     public static Resources getresource(){
        return   mContext.getResources();
     }
     public static boolean isNetworkEnable(){
          ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
          NetworkInfo info = manager.getActiveNetworkInfo();
          if(info!=null && info.isAvailable()){
               return true;
          }else {
               //todo
          }
               return false;

     }

}
