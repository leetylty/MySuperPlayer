package com.example.administrator.mysuperplayer.Detail;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mysuperplayer.R;
import com.example.administrator.mysuperplayer.api.OnGetDetailAlbumsListener;
import com.example.administrator.mysuperplayer.api.SiteApi;
import com.example.administrator.mysuperplayer.base.BaseActivity;
import com.example.administrator.mysuperplayer.model.Album;
import com.example.administrator.mysuperplayer.model.ErrorInfo;
import com.example.administrator.mysuperplayer.model.Site;
import com.example.administrator.mysuperplayer.util.ImageUtil;

public class AlbumDetailActivity extends BaseActivity {
    private static final  String TAG = "AlbumDetailActivity";
    private Album mAlbum;
    private boolean isShowDesc;
    private int videoNo;
    private ImageView albumImg;
    private TextView tvAlbumName;
    private TextView tvAlbumMainactor;
    private TextView tvAlbumDirector;
    private TextView tvAlbumDesc;
    private boolean mIsFavour ;



    @Override
    protected int getViewId() {
        return R.layout.activity_album_detail;
    }

    @Override
    protected void initView() {
        mAlbum = getIntent().getParcelableExtra("album");
        isShowDesc = getIntent().getBooleanExtra("isShowDesc", true);
        videoNo = getIntent().getIntExtra("videoNo", 0);
        setSupportActionBar();
        setSupportArrowActionBar(true);
        setTitle(mAlbum.getTitle());

        tvAlbumName = bindView(R.id.tv_album_name);//名字，海报图，主演，导演，描述
        albumImg = bindView(R.id.iv_album_image);
        tvAlbumDirector = bindView(R.id.tv_album_director);
        tvAlbumMainactor = bindView(R.id.tv_album_mainactor);
        tvAlbumDesc = bindView(R.id.tv_album_desc);
    }

    @Override
    protected void initData() {
        //名字
        tvAlbumName.setText(mAlbum.getTitle());
        //导演
        if(!TextUtils.isEmpty(mAlbum.getMainActor())){
            tvAlbumDirector.setText("导演:"+mAlbum.getMainActor());
            tvAlbumDirector.setVisibility(View.VISIBLE);
        }else {
            tvAlbumDirector.setVisibility(View.GONE);
        }
        //主演
        if(!TextUtils.isEmpty(mAlbum.getHorImgUrl())){
            tvAlbumMainactor.setText("主演:"+mAlbum.getHorImgUrl());
            tvAlbumMainactor.setVisibility(View.VISIBLE);
        }else {
            tvAlbumMainactor.setVisibility(View.GONE);
        }
        //描述
        if(!TextUtils.isEmpty(mAlbum.getAlubmDesc())){
            tvAlbumDesc.setText(mAlbum.getAlubmDesc());
            tvAlbumDesc.setVisibility(View.VISIBLE);
        }else {
            tvAlbumDesc.setVisibility(View.GONE);
        }
        //海报图
        if (!TextUtils.isEmpty(mAlbum.getVerImgUrl())) {
            ImageUtil.disPlayImage(albumImg, mAlbum.getVerImgUrl());
        } else if (!TextUtils.isEmpty(mAlbum.getTip())) {
            ImageUtil.disPlayImage(albumImg, mAlbum.getTip());
        }

        SiteApi.onGetDetailAlbums(1, mAlbum, new OnGetDetailAlbumsListener() {
            @Override
            public void OnGetDetailSuccess(Album album) {
                Log.i(TAG,".....Success"+mAlbum.getVideototal());
                AlbumPlayGridFragment.newInstance(mAlbum,isShowDesc,0);

            }

            @Override
            public void OnGetDetailFail(ErrorInfo errorInfo) {

            }
        });

    }


    public static  void  launch(Activity activity, Album album,int videosNo,boolean isShowDesc){
        Intent intent = new Intent(activity,AlbumDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("album",album);
        intent.putExtra("videoNo",videosNo);
        intent.putExtra("isShowDesc",isShowDesc);
        activity.startActivity(intent);

    }
    public static  void  launch(Activity activity, Album album){
        Intent intent = new Intent(activity,AlbumDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("album",album);
        activity.startActivity(intent);

    }
    //按钮回退
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return  true;
            case R.id.action_favor_item:
               if(mIsFavour){
                   mIsFavour =false;
                   invalidateOptionsMenu();
                   Toast.makeText(this,"已经取消收藏",Toast.LENGTH_SHORT).show();
               }
                return true;
            case R.id.action_unfavor_item:
                if(!mIsFavour){
                    mIsFavour =true;
                    invalidateOptionsMenu();
                    Toast.makeText(this,"已添加消收藏",Toast.LENGTH_SHORT).show();
                }
              return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem favouritem = menu.findItem(R.id.action_favor_item);
        MenuItem unfavouritem = menu.findItem(R.id.action_unfavor_item);
        favouritem.setVisible(mIsFavour);
        unfavouritem.setVisible(!mIsFavour);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.album_detail_menu,menu);
        return true;
    }
}
