package com.example.administrator.mysuperplayer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SlapActivity extends AppCompatActivity {


    private SharedPreferences sp;
    public static final int GO_GUIDE=1;
    public static final int GO_HOME=2;
    public static final int DURTION_TIME=2000;

    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GO_GUIDE:
                    goToGuideActivity();
                    break;
                case GO_HOME:
                    goToHomeActivity();
                    break;
                default:
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slap);

        sp = getSharedPreferences("config",MODE_PRIVATE);
        init();
        ininView();
    }
    //初始化View 布局
    private void ininView() {
    }

    //如果是首次则进入引导 ，不是的话进入主页
    private void init() {
       Boolean isFirstIn =sp.getBoolean("isFirstIn",true);
        if(isFirstIn){
              handler.sendEmptyMessageDelayed(GO_GUIDE,DURTION_TIME);
        }else {
            handler.sendEmptyMessageDelayed(GO_HOME,DURTION_TIME);
        }

    }

   //进入引导页
    private void goToGuideActivity(){
        Intent intent =new Intent(SlapActivity.this,GuideActivity.class);
        startActivity(intent);
        finish();

    }
    //进入主页
    private void goToHomeActivity(){
        Intent intent =new Intent(SlapActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}












//1.创建sp对象用于判断是否是第一次
//2, 用handler 进行判断处理，是第一次进引导不是第一次进主页