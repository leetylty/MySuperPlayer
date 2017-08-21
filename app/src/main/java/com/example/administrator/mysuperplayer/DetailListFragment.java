package com.example.administrator.mysuperplayer;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.administrator.mysuperplayer.base.BaseFragment;

/**
 * Created by Administrator on 2017/8/18.
 */

public class DetailListFragment extends BaseFragment {
    private static  int msiteId;
    private static int mChannId;
    public static final String CHANN_ID ="channid";
    public static final String SITE_ID ="siteid";


   public DetailListFragment (){

    }

    @Override
    protected int getLayoutId() {
        return R.layout.detail_fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    public static Fragment newInstance (int siteId, int channId){
        DetailListFragment fragment = new DetailListFragment();
        msiteId=siteId;
        mChannId = channId;
        Bundle bundle = new Bundle();
        bundle.putInt(CHANN_ID,channId);
        bundle.putInt(SITE_ID,siteId);
        fragment.setArguments(bundle);
        return  fragment;
    }
}
