package com.example.administrator.mysuperplayer.api;

import com.example.administrator.mysuperplayer.model.Channel;

/**
 * Created by Administrator on 2017/8/21.
 */

public abstract class BaseSiteApi {

   public abstract void onGetChannelAlbums(Channel channel,int pageNum, int pageSize, OnGetChannelAlbumsListener listener);
}
