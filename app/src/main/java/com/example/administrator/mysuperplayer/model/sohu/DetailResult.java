package com.example.administrator.mysuperplayer.model.sohu;

import com.google.gson.annotations.Expose;

/**
 * 搜狐频道数据返回集合
 */

public class DetailResult {

    @Expose
    private  long status;
    @Expose
    private String statusText;

    //详情页
    @Expose
    private ResultAlbum data ;


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


    public ResultAlbum getmResultAlbum() {
        return data;
    }

    public void setmResultAlbum(ResultAlbum mResultAlbum) {
        this.data = mResultAlbum;
    }

}
