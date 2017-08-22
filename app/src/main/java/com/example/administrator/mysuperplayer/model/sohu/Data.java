package com.example.administrator.mysuperplayer.model.sohu;

import com.example.administrator.mysuperplayer.model.Album;
import com.example.administrator.mysuperplayer.model.AlbumList;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */

public class Data {

    @Expose
    public  int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ResultAlbum> getResultAlbumList() {
        return resultAlbumList;
    }

    public void setResultAlbumList(List<ResultAlbum> resultAlbumList) {
        this.resultAlbumList = resultAlbumList;
    }

    @Expose

    private List<ResultAlbum> resultAlbumList;
}
