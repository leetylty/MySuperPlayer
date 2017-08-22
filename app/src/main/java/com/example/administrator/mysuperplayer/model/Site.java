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


    public int getChannelId() {
        return siteId;
    }


    public String getChanneName() {
        return siteName;
    }


    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public Site(int id) {

        siteId =id;

        switch (siteId){
            case LETV:
                siteName = "乐视视频";
                break;
            case SOHU:
                siteName = "搜狐视频";
                break;

        }
    }
}
