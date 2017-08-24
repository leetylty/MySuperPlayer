package com.example.administrator.mysuperplayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.mysuperplayer.api.OnGetChannelAlbumsListener;
import com.example.administrator.mysuperplayer.api.SiteApi;
import com.example.administrator.mysuperplayer.base.BaseFragment;
import com.example.administrator.mysuperplayer.model.Album;
import com.example.administrator.mysuperplayer.model.AlbumList;
import com.example.administrator.mysuperplayer.model.Channel;
import com.example.administrator.mysuperplayer.model.ErrorInfo;
import com.example.administrator.mysuperplayer.model.Site;
import com.example.administrator.mysuperplayer.util.ImageUtil;
import com.example.administrator.mysuperplayer.widget.PullLoadRecyclerView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/18.
 */

public class DetailListFragment extends BaseFragment {
    private static final String TAG = "DetailListFragment";
    private int msiteId;
    private int mChannId;
    public static final String CHANN_ID = "channid";
    public static final String SITE_ID = "siteid";
    private PullLoadRecyclerView mpullloadrecyclerview;
    private TextView emptyView;
    public int Columns = 2;
    private DetailListAdapter mAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    public static final int REFRESH = 1500;
    public static final int LOADMORE = 3000;
    private int pageNo;
    private int pageSize = 30;


    public DetailListFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            msiteId = getArguments().getInt(SITE_ID);
            mChannId = getArguments().getInt(CHANN_ID);
            Log.i(TAG, "------+频道ID" + msiteId + "内容ID" + mChannId);
        }
        pageNo = 0;
        mAdapter = new DetailListAdapter(getActivity(), new Channel(mChannId, getActivity()));
        LoadData();
        if (msiteId == Site.LETV) {
            Columns = 2;//乐视频道相关2列
            mAdapter.SetColumns(Columns);

        } else {
            Columns = 3;
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
        mpullloadrecyclerview = bindView(R.id.pullloadrecyclerview);
        mpullloadrecyclerview.SetGridLayout(Columns);
        mpullloadrecyclerview.SetAdapter(mAdapter);
        mpullloadrecyclerview.setOnPullLoadMoreListener(new PullLoadMoreListener());

    }

    class PullLoadMoreListener implements PullLoadRecyclerView.OnPullLoadMoreListener {

        @Override
        public void refresh() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    reFreshData();
                    mpullloadrecyclerview.setRefreshCompleted();

                }
            }, REFRESH);

        }

        @Override
        public void loadmore() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoadData();
                    mpullloadrecyclerview.SetLoadMoreCompleted();

                }
            }, LOADMORE);

        }
    }

    //加载数据
    private void LoadData() {
        pageNo++;
        SiteApi.onGetChannelAlbums(getActivity(), pageNo, pageSize, msiteId, mChannId, new OnGetChannelAlbumsListener() {
            @Override
            public void OnGetChannelSuccess(ArrayList<Album> albumList) {
                Log.i(TAG,">>>album执行了！-----");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        emptyView.setVisibility(View.VISIBLE);

                    }
                });
                for (Album album : albumList) {
                    mAdapter.setData(album);
                    Log.i(TAG,">>>album-----"+album.toString());
                }


                mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            @Override
            public void OnGetChannelFail(ErrorInfo errorInfo) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        emptyView.setText(getActivity().getResources().getString(R.string.data_failed_tip));
                        Log.i(TAG,"错误数据监听>>>>");
                    }
                });
            }
        });

    }

    //刷新数据
    private void reFreshData() {
        //请求接口 加载数据


    }

    class DetailListAdapter extends RecyclerView.Adapter {
         private Context mContext;
         private Channel mChannel;
         private AlbumList mAlbumList = new AlbumList();
         private int  mColumns;

        public DetailListAdapter(Context context,Channel channel) {
             mContext =context;
            mChannel = channel;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
          View view =  ((Activity)mContext).getLayoutInflater().inflate(R.layout.detail_list_item,null);
            ItemViewHolder holder  =  new ItemViewHolder(view);
            view.setTag(holder);

            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(mAlbumList.size()==0){

                return;
            }
          final Album album = getIem(position);
            if (holder instanceof ItemViewHolder){
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.albumName.setText(album.getTitle());
                if (album.getTip().isEmpty()){
                    itemViewHolder.albumTip.setVisibility(View.GONE);
                }else {
                    itemViewHolder.albumTip.setText(album.getTip());
                }
                //重新计算宽高
               Point point = ImageUtil.getVerPotSize(mContext,Columns);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(point.x,point.y);
                itemViewHolder.albumPost.setLayoutParams(params);
                if(album.getVerImgUrl()!= null){
                    ImageUtil.disPlayImage(itemViewHolder.albumPost,album.getVerImgUrl(),point.x,point.y);
                }else {

                }
            }
        }

        private Album getIem(int positon){

            return mAlbumList.get(positon);
        }

        @Override
        public int getItemCount() {
            if(mAlbumList.size()>0){
                return mAlbumList.size();
            }
            return 0;
        }


        //显示列数
        public void SetColumns(int columns) {
            mColumns = columns;

        }

        public void setData(Album album) {
            mAlbumList.add(album);

        }

        public  class ItemViewHolder extends RecyclerView.ViewHolder{
            private LinearLayout resultContainer;
            private ImageView albumPost;
            private TextView albumName;
            private TextView albumTip;


            public ItemViewHolder(View itemView) {
                super(itemView);
                albumName = (TextView)itemView.findViewById(R.id.tv_album_name);
                albumTip = (TextView) itemView.findViewById(R.id.tv_album_tip);
                albumPost = (ImageView) itemView.findViewById(R.id.iv_album_poster);
                resultContainer = (LinearLayout) itemView.findViewById(R.id.album_container);


            }
        }

    }


    public static Fragment newInstance(int siteId, int channId) {
        DetailListFragment fragment = new DetailListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CHANN_ID, channId);
        bundle.putInt(SITE_ID, siteId);
        fragment.setArguments(bundle);
        return fragment;
    }


}
