package com.example.administrator.mysuperplayer.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/8/16.
 */

public abstract class BaseFragment extends Fragment {

      private  View mContentView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


       mContentView=this.getActivity().getLayoutInflater().inflate(getLayoutId(),container,false);


        initView();
        initData();
        return mContentView;


    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initView();
    //泛型基类  作用于控件识别
    protected <T extends View> T  bindView(int resId){
        return (T) mContentView.findViewById(resId);
    }
}
