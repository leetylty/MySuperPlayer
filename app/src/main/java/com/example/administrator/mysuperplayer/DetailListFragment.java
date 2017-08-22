package com.example.administrator.mysuperplayer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.mysuperplayer.api.OnGetChannelAlbumsListener;
import com.example.administrator.mysuperplayer.api.SiteApi;
import com.example.administrator.mysuperplayer.base.BaseFragment;
import com.example.administrator.mysuperplayer.model.Album;
import com.example.administrator.mysuperplayer.model.ErrorInfo;
import com.example.administrator.mysuperplayer.model.Site;
import com.example.administrator.mysuperplayer.widget.PullLoadRecyclerView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/18.
 */

public class DetailListFragment extends BaseFragment {
    private static final String TAG ="DetailListFragment";
    private static  int msiteId;
    private static int mChannId;
    public static final String CHANN_ID ="channid";
    public static final String SITE_ID ="siteid";
    private PullLoadRecyclerView mpullloadrecyclerview;
    private TextView emptyView;
    public  int Columns = 2;
    private DetailListAdapter mAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    public static final  int REFRESH=1500;
    public static final  int LOADMORE=3000;
    private int pageNo;
    private int pageSize=30;


    public DetailListFragment (){

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNo=0;
        LoadData();
        mAdapter = new DetailListAdapter();
        if (msiteId == Site.LETV){
            Columns =2;//乐视频道相关2列
            mAdapter.SetColumns(Columns);

        }

    }


    @Override
    protected int getLayoutId() {
        return R.layout.detail_fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        emptyView = bindView(R.id.tv_empty);
        emptyView.setText(getActivity().getResources().getString(R.string.load_more_text));
        mpullloadrecyclerview =   bindView(R.id.pullloadrecyclerview);
        mpullloadrecyclerview.setGravity(3);
        mpullloadrecyclerview.SetAdapter(mAdapter);
        mpullloadrecyclerview.setOnPullLoadMoreListener(new PullLoadMoreListener());

    }

    class PullLoadMoreListener implements PullLoadRecyclerView.OnPullLoadMoreListener{

        @Override
        public void refresh() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    reFreshData();
                    mpullloadrecyclerview.setRefreshCompleted();

                }
            },REFRESH);

        }

        @Override
        public void loadmore() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoadData();
                    mpullloadrecyclerview.SetLoadMoreCompleted();

                }
            },LOADMORE);

        }
    }
    //加载数据
    private void LoadData() {
        pageNo++;
        SiteApi.onGetChannelAlbums(getActivity(), pageNo, pageSize, 2, mChannId, new OnGetChannelAlbumsListener() {
            @Override
            public void OnGetChannelSuccess(ArrayList<Album> albumList) {
                for (Album album : albumList){
                    Log.i(TAG,">>>album-----"+album.toString());
                }
            }

            @Override
            public void OnGetChannelFail(ErrorInfo errorInfo) {

            }
        });

    }

    //刷新数据
    private void reFreshData() {
        //请求接口 加载数据


    }

    class DetailListAdapter extends RecyclerView.Adapter{
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
        public  void  SetColumns (int columns){

        }

    }


    public static Fragment newInstance (int siteId, int channId){
        DetailListFragment fragment = new DetailListFragment();
        msiteId=siteId;
        mChannId = channId;
        Bundle bundle = new Bundle();
        bundle.putInt(CHANN_ID,channId);
        bundle.putInt(SITE_ID,siteId);
        fragment.setArguments(bundle);
        return  fragment;
    }



}
