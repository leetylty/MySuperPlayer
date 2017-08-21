package com.example.administrator.mysuperplayer.model;

import android.content.Context;

import com.example.administrator.mysuperplayer.R;

import static com.example.administrator.mysuperplayer.model.Channel.SHOW;

/**
 * Created by Administrator on 2017/8/18.
 */

public class Site {


    public static final int LETV = 1;//乐视
    public static final int SOHU = 2;//搜狐
    public static final int MAX_SIZE =2;



    private int siteId;
    private String siteName;
    private Context mContext;

    public int getChannelId() {
        return siteId;
    }


    public String getChanneName() {
        return siteName;
    }



    public Site(int id, Context context) {
        siteId =id;
        mContext= context;
        switch (siteId){
            case LETV:
                siteName = mContext.getResources().getString(R.string.site_letv);
                break;
            case SOHU:
                siteName = mContext.getResources().getString(R.string.site_sohu);
                break;

        }
    }
}
