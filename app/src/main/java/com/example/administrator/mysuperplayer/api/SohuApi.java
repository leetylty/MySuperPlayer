package com.example.administrator.mysuperplayer.api;

import android.support.annotation.Nullable;

import com.example.administrator.mysuperplayer.AppManager;
import com.example.administrator.mysuperplayer.model.Album;
import com.example.administrator.mysuperplayer.model.AlbumList;
import com.example.administrator.mysuperplayer.model.Channel;
import com.example.administrator.mysuperplayer.model.ErrorInfo;
import com.example.administrator.mysuperplayer.model.Site;
import com.example.administrator.mysuperplayer.model.sohu.DetailResult;
import com.example.administrator.mysuperplayer.model.sohu.Result;
import com.example.administrator.mysuperplayer.model.sohu.ResultAlbum;
import com.example.administrator.mysuperplayer.util.OkHttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/21.
 */

public class SohuApi extends BaseSiteApi {
    private static final String TAG = SohuApi.class.getSimpleName();
    private static final int SOHU_CHANNELID_MOVIE = 1; //搜狐电影频道ID
    private static final int SOHU_CHANNELID_SERIES = 2; //搜狐电视剧频道ID
    private static final int SOHU_CHANNELID_VARIETY = 7; //搜狐综艺频道ID
    private static final int SOHU_CHANNELID_DOCUMENTRY = 8; //搜狐纪录片频道ID
    private static final int SOHU_CHANNELID_COMIC = 16; //搜狐动漫频道ID
    private static final int SOHU_CHANNELID_MUSIC = 24; //搜狐音乐频道ID
    private static final String API_CHANNEL_ABLUM_FORMAT ="http://api.tv.sohu.com/v4/search/channel.json" +
            "?cid=%s&o=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&" +
            "sver=6.2.0&sysver=4.4.2&partner=47&page=%s&page_size=%s";

    //http://api.tv.sohu.com/v4/album/videos/9112373.json?page=1&page_size=50&order=0&site=1&with_trailer=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47
    //http://api.tv.sohu.com/v4/album/info/9112373.json?plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47
    private final static String API_KEY = "plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47";
    private final static String API_ALBUM_INFO = "http://api.tv.sohu.com/v4/album/info/";
    //http://api.tv.sohu.com/v4/search/channel.json?cid=2&o=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47&page=1&page_size=1

    @Override
    public void onGetChannelAlbums(Channel channel, int pageNum, int pageSize, OnGetChannelAlbumsListener listener) {

        String url = getChannelUrl(channel,pageNum,pageSize);
        doGetChannelAlbumByUrl(url,listener);

    }
    //进行网络请求操作
    public void doGetChannelAlbumByUrl(final String url, final OnGetChannelAlbumsListener listener) {
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
                //取到映射数据ResultAlbum, 转换ResultAlbum成Album,存到AlbumList中
                Result result = AppManager.getGson().fromJson(response.body().string(), Result.class);
                AlbumList albumlist = toConverAlbumList(result);
                if(albumlist!=null){
                    if (albumlist.size()>0 && listener!=null){
                        listener.OnGetChannelSuccess(albumlist);
                    }
                }else {
                    ErrorInfo info = buildErrorInfo(url,"doGetChannelAlbumByUrl",null,ErrorInfo.ERROR_TYPE_DATA_CONVERT);
                    listener.OnGetChannelFail(info);
                }


            }
        });
    }

    //存入ResultAlbum数据


    private AlbumList toConverAlbumList(Result mresult) {

        if (mresult.getData().getResultAlbumList().size()>0){
           AlbumList albumList = new AlbumList();
            for (ResultAlbum resultAlbum :mresult.getData().getResultAlbumList()){
                Album album = new Album(Site.SOHU);
                album.setAlubmDesc(resultAlbum.getTvDesc());
                album.setAlubmId(resultAlbum.getAlbumId());
                album.setHorImgUrl(resultAlbum.getHorHighPic());
                album.setVerImgUrl(resultAlbum.getVerHighPic());
                album.setMainActor(resultAlbum.getMainActor());
                album.setTip(resultAlbum.getTip());
                album.setTitle(resultAlbum.getAlbumName());
                album.setDirctor(resultAlbum.getDirector());
                albumList.add(album);
            }
            return albumList;
        }
        return null;
    }

    private ErrorInfo buildErrorInfo(String url,String functionName,Exception e,int type){
        ErrorInfo errorinfo = new ErrorInfo(Site.SOHU,type);
        errorinfo.setFunctionName(functionName);
        errorinfo.setUrl(url);
        errorinfo.setTag(TAG);
        errorinfo.setClassName(TAG);
        return errorinfo;

    }

    private String getChannelUrl(Channel channel, int pageNum, int pageSize){
          //格式化URL
        return String.format(API_CHANNEL_ABLUM_FORMAT,ToConvertChannelId(channel),pageNum,pageSize);
    }
   //自定义频道ID 与真实ID 转换
    private  int ToConvertChannelId(Channel channel){
        int channelId = -1;
        switch (channel.getChannelId()){
            case Channel.SHOW:
                channelId = SOHU_CHANNELID_SERIES;
                break;
            case Channel.MOVIE:
                channelId = SOHU_CHANNELID_MOVIE;
                break;
            case Channel.MUSIC:
                channelId = SOHU_CHANNELID_MUSIC;
                break;
            case Channel.COMIC:
                channelId = SOHU_CHANNELID_COMIC;
                break;
            case Channel.DOCUMENTRY:
                channelId = SOHU_CHANNELID_DOCUMENTRY;
                break;
            case Channel.VARITEM:
                channelId = SOHU_CHANNELID_VARIETY;
                break;
        }
        return  channelId;
    }

    public void  onGetDetailAlbums(final Album  album, final OnGetDetailAlbumsListener listener){
       final String url = API_ALBUM_INFO + album.getAlubmIdl()+".json?"+API_KEY;
        OkHttpUtil.excute(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(listener!=null){
                    ErrorInfo info = buildErrorInfo(url,"onGetDetailAlbums",e,ErrorInfo.ERROR_TYPE_URL);
                    listener.OnGetDetailFail(info);
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    ErrorInfo info = buildErrorInfo(url, "onGetDetailAlbums", null, ErrorInfo.ERROR_TYPE_HTTP);
                    listener.OnGetDetailFail(info);
                    return;
                }
                //DATA
              DetailResult result =  AppManager.getGson().fromJson(response.body().string(),DetailResult.class);
              if(result.getmResultAlbum()!=null){
                  if (result.getmResultAlbum().getLastVideoCount()>0){
                      album.setVideototal(result.getmResultAlbum().getLastVideoCount());
                  }else {
                      album.setVideototal(result.getmResultAlbum().getLastVideoCount());
                  }

              }
              //加载完数据后进行通知
              if(listener!=null){
                  listener.OnGetDetailSuccess(album);
              }
            }
        });

    }

    //取video信息
    public void onGetVideoAlbums(Album album, OnGetVideoAlbumsListener listener){

    }
}
