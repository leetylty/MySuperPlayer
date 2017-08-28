package com.example.administrator.mysuperplayer.model.sohu;

import com.google.gson.annotations.Expose;

/**
 * 搜狐频道数据返回集合
 */

public class Result {

    @Expose
    private  long status;
    @Expose
    private String statusText;
    //列表页
    @Expose
    private Data data ;
    //详情页
    @Expose
    private ResultAlbum mResultAlbum ;


    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public ResultAlbum getmResultAlbum() {
        return mResultAlbum;
    }

    public void setmResultAlbum(ResultAlbum mResultAlbum) {
        this.mResultAlbum = mResultAlbum;
    }

}
