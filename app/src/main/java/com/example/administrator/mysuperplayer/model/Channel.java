package com.example.administrator.mysuperplayer.model;

import android.content.Context;

import com.example.administrator.mysuperplayer.R;

import java.io.Serializable;
import java.io.SerializablePermission;

/**
 * Created by Administrator on 2017/8/18.
 */

public class Channel implements Serializable {

    public static final int SHOW = 1;//电视剧
    public static final int MOVIE = 2;//电影
    public static final int COMIC = 3;//动漫
    public static final int DOCUMENTRY = 4;//纪录片
    public static final int MUSIC = 5;//音乐
    public static final int VARITEM = 6;//综艺
    public static final int LIVE = 7;//直播
    public static final int FAVOURITE = 8;//收藏
    public static final int HISTORY = 9;//历史记录
    public static final int MAX_COUNT = 9;//频道数


    private int channelId;
    private String channeName;
    private Context mContext;

    public int getChannelId() {
        return channelId;
    }


    public String getChanneName() {
        return channeName;
    }



    public Channel(int id, Context context) {
      channelId =id;
        mContext= context;
        switch (channelId){
            case SHOW:
                channeName = mContext.getResources().getString(R.string.channel_series);
            break;
            case MOVIE:
                channeName = mContext.getResources().getString(R.string.channel_movie);
                break;
            case COMIC:
                channeName = mContext.getResources().getString(R.string.channel_comic);
                break;
            case DOCUMENTRY:
                channeName = mContext.getResources().getString(R.string.channel_documentary);
                break;
            case MUSIC:
                channeName = mContext.getResources().getString(R.string.channel_music);
                break;
            case VARITEM:
                channeName = mContext.getResources().getString(R.string.channel_variety);
                break;
            case LIVE:
                channeName = mContext.getResources().getString(R.string.channel_live);
                break;
            case FAVOURITE:
                channeName = mContext.getResources().getString(R.string.channel_favorite);
                break;
            case HISTORY:
                channeName = mContext.getResources().getString(R.string.channel_history);
                break;
        }
    }
}
