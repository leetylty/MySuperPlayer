package com.example.administrator.mysuperplayer.base;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.mysuperplayer.R;

/**
 * Created by Administrator on 2017/8/16.
 */

public  abstract class BaseActivity extends AppCompatActivity {

    protected android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());
        initView();
        initData();
    }

    protected  abstract int getViewId();
    protected  abstract void initView();
    protected  abstract void  initData();
  //泛型基类  作用于控件识别
    protected <T extends View> T  bindView(int resId){
        return (T) findViewById(resId);
    }

    protected  void  setSupportActionBar(){
       mToolbar= bindView(R.id.toolbar);
        if(mToolbar!=null){
            setSupportActionBar(mToolbar);

        }
    }

    protected  void  setSupportActionBarIcon(int resId){
        mToolbar = bindView(R.id.toolbar);
        if (mToolbar!=null){
            mToolbar.setNavigationIcon(resId);
        }
    }


}
