package com.example.administrator.mysuperplayer.api;

import com.example.administrator.mysuperplayer.model.Album;
import com.example.administrator.mysuperplayer.model.ErrorInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/21.
 */

public interface   OnGetChannelAlbumsListener {

    void OnGetChannelSuccess(ArrayList<Album> albumList);
    void OnGetChannelFail(ErrorInfo errorInfo);
}
