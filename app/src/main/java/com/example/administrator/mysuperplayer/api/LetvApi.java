package com.example.administrator.mysuperplayer.api;

import android.util.Log;

import com.example.administrator.mysuperplayer.model.Album;
import com.example.administrator.mysuperplayer.model.AlbumList;
import com.example.administrator.mysuperplayer.model.Channel;
import com.example.administrator.mysuperplayer.model.ErrorInfo;
import com.example.administrator.mysuperplayer.model.Site;
import com.example.administrator.mysuperplayer.util.OkHttpUtil;


import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/21.
 */

public class LetvApi extends BaseSiteApi {
    private static final String TAG = LetvApi.class.getSimpleName();
    private static final int LETV_CHANNELID_MOVIE = 1; //乐视电影频道ID
    private static final int LETV_CHANNELID_SERIES = 2; //乐视电视剧频道ID
    private static final int LETV_CHANNELID_VARIETY = 11; //乐视综艺频道ID
    private static final int LETV_CHANNELID_DOCUMENTRY = 16; //乐视纪录片频道ID
    private static final int LETV_CHANNELID_COMIC = 5; //乐视动漫频道ID
    private static final int LETV_CHANNELID_MUSIC = 9; //乐视音乐频道ID

    //http://static.meizi.app.m.letv.com/android/mod/mob/ctl/listalbum/act/index/src/1/cg/2/or/20/vt/180001/ph/420003,420004/pt/-141003/pn/1/ps/30/pcode/010110263/version/5.6.2.mindex.html
    private final static String ALBUM_LIST_URL_FORMAT = "http://static.meizi.app.m.letv.com/android/" +
            "mod/mob/ctl/listalbum/act/index/src/1/cg/%s/ph/420003,420004/pn/%s/ps/%s/pcode/010110263/version/5.6.2.mindex.html";

    private final static String ALBUM_LIST_URL_DOCUMENTARY_FORMAT = "http://static.meizi.app.m.letv.com/android/" +
            "mod/mob/ctl/listalbum/act/index/src/1/cg/%s/or/3/ph/420003,420004/pn/%s/ps/%s/pcode/010110263/version/5.6.2.mindex.html";

    private final static String ALBUM_LIST_URL_SHOW_FORMAT = "http://static.meizi.app.m.letv.com/android/" +
            "mod/mob/ctl/listalbum/act/index/src/1/cg/%s/or/20/vt/180001/ph/420003,420004/pt/-141003/pn/%s/ps/%s/pcode/010110263/version/5.6.2.mindex.html";

    @Override
    public void onGetChannelAlbums(Channel channel, int pageNum, int pageSize, OnGetChannelAlbumsListener listener) {
        String url = GetChannelUrl(channel,pageNum,pageSize);
        doGetChannelAlbumByLETVUrl(url,listener);


    }
    private String GetChannelUrl(Channel channel,int pageNum, int pageSize) {
        if(channel.getChannelId() == Channel.DOCUMENTRY){
            return String.format(ALBUM_LIST_URL_DOCUMENTARY_FORMAT,ToConvertChannleId(channel),pageNum,pageSize);
        }else if(channel.getChannelId() == Channel.DOCUMENTRY){
            return String.format(ALBUM_LIST_URL_SHOW_FORMAT,ToConvertChannleId(channel),pageNum,pageSize);
        }
        return String.format(ALBUM_LIST_URL_FORMAT,ToConvertChannleId(channel),pageNum,pageSize);

    }
     //进行网络请求
    private void doGetChannelAlbumByLETVUrl(final String url , final OnGetChannelAlbumsListener listener) {
        OkHttpUtil.excute(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(listener!=null){
                    ErrorInfo info = buildErrorInfo(url,"doGetChannelAlbumByUrl",e,ErrorInfo.ERROR_TYPE_URL);
                   listener.OnGetChannelFail(info);
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    ErrorInfo info = buildErrorInfo(url, "doGetChannelAlbumByUrl", null, ErrorInfo.ERROR_TYPE_HTTP);
                    listener.OnGetChannelFail(info);
                    return;
                }


                            String json = response.body().string();
                            try {
                                JSONObject resultJson = new JSONObject(json);
                                JSONObject bodyJson = resultJson.optJSONObject("body");
                                if (bodyJson.optInt("album_count") > 0) {
                                    AlbumList list = new AlbumList();
                                    JSONArray albumListJosn = bodyJson.optJSONArray("album_list");
                                    for (int i = 0; i< albumListJosn.length(); i++) {
                                        Album album = new Album(Site.LETV);
                                        JSONObject albumJson = albumListJosn.getJSONObject(i);
                                        album.setAlubmId(albumJson.getString("aid"));
                                        album.setAlubmDesc(albumJson.getString("subname"));
                                        album.setTitle(albumJson.getString("name"));
                                        album.setTip(albumJson.getString("subname"));
                                        JSONObject jsonImage = albumJson.getJSONObject("images");
                                        //读取【400*300】字符
                                        String imageurl = StringEscapeUtils.unescapeJava(jsonImage.getString("400*300"));
                                        album.setHorImgUrl(imageurl);
                                        list.add(album);

                        }
                        if(list!=null){
                            if (list.size()>0 && listener!=null){
                                listener.OnGetChannelSuccess(list);
                            }
                        }else {
                            ErrorInfo info = buildErrorInfo(url,"doGetChannelAlbumByUrl",null,ErrorInfo.ERROR_TYPE_DATA_CONVERT);
                            listener.OnGetChannelFail(info);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    ErrorInfo info = buildErrorInfo(url,"doGetChannelAlbumByUrl",null,ErrorInfo.ERROR_TYPE_PARSE_JSON);
                    listener.OnGetChannelFail(info);
                }


            }

        });


    }




    //自定义频道ID 与真实ID 转换
    private  int ToConvertChannleId(Channel channel){
        int channelId = -1;
        switch (channel.getChannelId()){
            case Channel.SHOW:
                channelId = LETV_CHANNELID_SERIES;
                break;
            case Channel.MOVIE:
                channelId = LETV_CHANNELID_MOVIE;
                break;
            case Channel.MUSIC:
                channelId = LETV_CHANNELID_MUSIC;
                break;
            case Channel.COMIC:
                channelId = LETV_CHANNELID_COMIC;
                break;
            case Channel.DOCUMENTRY:
                channelId = LETV_CHANNELID_DOCUMENTRY;
                break;
            case Channel.VARITEM:
                channelId = LETV_CHANNELID_VARIETY;
                break;
        }
        return  channelId;
    }
    private ErrorInfo buildErrorInfo(String url,String functionName,Exception e,int type){
        ErrorInfo errorinfo = new ErrorInfo(Site.LETV,type);
        errorinfo.setFunctionName(functionName);
        errorinfo.setUrl(url);
        errorinfo.setTag(TAG);
        errorinfo.setClassName(TAG);
        return errorinfo;

    }
}
