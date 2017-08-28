package com.example.administrator.mysuperplayer.api;

import com.example.administrator.mysuperplayer.model.Album;
import com.example.administrator.mysuperplayer.model.ErrorInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/28.
 */

public interface OnGetVideoAlbumsListener {
    void OnGetVideolSuccess(ArrayList<Album> albumList);
    void OnGetVideoFail(ErrorInfo errorInfo);
}
