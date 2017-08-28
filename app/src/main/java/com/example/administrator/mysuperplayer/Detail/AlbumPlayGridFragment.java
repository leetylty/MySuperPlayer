package com.example.administrator.mysuperplayer.Detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.administrator.mysuperplayer.api.OnGetVideoAlbumsListener;
import com.example.administrator.mysuperplayer.api.SiteApi;
import com.example.administrator.mysuperplayer.base.BaseFragment;
import com.example.administrator.mysuperplayer.model.Album;
import com.example.administrator.mysuperplayer.model.ErrorInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/28.
 */

public class AlbumPlayGridFragment extends BaseFragment {

    private static final String ARGS_ALBUM="album";
    private static final String ARGS_ISSHOWDESC="isshowdesc";
    private static final String ARGS_INITPOSITION="initvideoposition";
    private Album mAlbum;
    private boolean mIsShowdesc;
    private int initVideoPosition;
    private int PagerNo;
    private int PagerSize;

    public AlbumPlayGridFragment(){

    }

    public static Fragment newInstance(Album album, boolean isShowDesc,int initVideoPosition){
        AlbumPlayGridFragment fragment = new AlbumPlayGridFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_ALBUM,album);
        bundle.putBoolean(ARGS_ISSHOWDESC,isShowDesc);
        bundle.putInt(ARGS_INITPOSITION,initVideoPosition);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mAlbum = getArguments().getParcelable(ARGS_ALBUM);
            mIsShowdesc = getArguments().getBoolean(ARGS_ISSHOWDESC);
            initVideoPosition =getArguments().getInt(ARGS_INITPOSITION);
            LoadData();
        }
    }
    //加载数据
    private void LoadData() {
            SiteApi.onGetVideoAlbums(1, mAlbum, new OnGetVideoAlbumsListener() {
                @Override
                public void OnGetVideolSuccess(ArrayList<Album> albumList) {

                }

                @Override
                public void OnGetVideoFail(ErrorInfo errorInfo) {

                }
            });
    }
}
