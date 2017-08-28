package com.example.administrator.mysuperplayer.api;

import com.example.administrator.mysuperplayer.model.Album;
import com.example.administrator.mysuperplayer.model.ErrorInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/28.
 */

public interface OnGetDetailAlbumsListener {
    void OnGetDetailSuccess(Album album);
    void OnGetDetailFail(ErrorInfo errorInfo);
}
