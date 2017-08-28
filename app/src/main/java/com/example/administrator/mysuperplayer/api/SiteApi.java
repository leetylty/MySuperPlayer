package com.example.administrator.mysuperplayer.api;

import android.content.Context;

import com.example.administrator.mysuperplayer.model.Album;
import com.example.administrator.mysuperplayer.model.Channel;
import com.example.administrator.mysuperplayer.model.Site;

/**
 * Created by Administrator on 2017/8/21.
 */

public  class SiteApi {
    public static void onGetChannelAlbums(Context context,int pageNum,int pageSize, int siteId, int channelId ,OnGetChannelAlbumsListener listener){
        switch (siteId){
            case Site.LETV:
               new LetvApi().onGetChannelAlbums(new Channel(channelId,context),pageNum,pageSize,listener);
                break;

            case Site.SOHU:
                new SohuApi().onGetChannelAlbums(new Channel(channelId,context),pageNum,pageSize,listener);
        }

    }

    public static void onGetDetailAlbums(int siteId , Album album, OnGetDetailAlbumsListener listener) {

        switch (siteId){
            case Site.LETV:
                new LetvApi().onGetDetailAlbums(album,listener);
                break;

            case Site.SOHU:
                new SohuApi().onGetDetailAlbums(album,listener);
        }
    }

    public static void onGetVideoAlbums(int siteId , Album album, OnGetVideoAlbumsListener listener) {

        switch (siteId){
            case Site.LETV:
                new LetvApi().onGetVideoAlbums(album,listener);
                break;

            case Site.SOHU:
                new SohuApi().onGetVideoAlbums(album,listener);
        }
    }
}
