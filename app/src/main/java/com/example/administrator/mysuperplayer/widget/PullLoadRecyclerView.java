package com.example.administrator.mysuperplayer.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Annotation;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.mysuperplayer.R;

import static com.example.administrator.mysuperplayer.R.drawable.e;
import static com.example.administrator.mysuperplayer.R.drawable.imooc_loading;

/**
 * Created by Administrator on 2017/8/21.
 */

public class PullLoadRecyclerView extends LinearLayout {
    private Context mContext;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private boolean isReFresh = false;//是否刷新
    private boolean isLoadMore = false;//是否加载更多
    private View mFootView;
    private AnimationDrawable mAnimationDrawable;//动画类型的Drawable
    private OnPullLoadMoreListener mOnPullLoadMoreListener;


    public PullLoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PullLoadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public PullLoadRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.pull_loadmore, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        //设置加载控件颜色渐变
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_blue_dark, android.R.color.holo_orange_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new MyReFreshListener());
        //处理RecylervIew;
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);//固定大小
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置默认动画
        mRecyclerView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                return isReFresh || isLoadMore;
            }
        });
        mRecyclerView.setVerticalScrollBarEnabled(false);//滚动条隐藏
        mRecyclerView.addOnScrollListener(new RecylerViewOnScroll());
        mFootView = view.findViewById(R.id.foot_view);
        ImageView imageView = (ImageView) mFootView.findViewById(R.id.foot_load_img);
        imageView.setBackgroundResource(R.drawable.imooc_loading);
        mAnimationDrawable =(AnimationDrawable)imageView.getBackground();
        TextView  textview  =(TextView) mFootView.findViewById(R.id.foot_load_tv);
        mFootView.setVisibility(View.GONE);
        //view  包含layout  RecylerView ,footView;
        this.addView(view);


    }

    private class MyReFreshListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            if (!isReFresh) {
                isReFresh = true;
                RefreshData();
            }
        }
    }

    //刷新数据
    private void RefreshData() {
        if(mOnPullLoadMoreListener!=null){
            mOnPullLoadMoreListener.refresh();
        }

    }

    class RecylerViewOnScroll extends RecyclerView.OnScrollListener{
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int firstItem = 0;
            int lastItem = 0;
            RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
            int totalcount = manager.getItemCount();
            if (manager instanceof GridLayoutManager){
                GridLayoutManager mGridLayoutManager = (GridLayoutManager)manager;
                firstItem = mGridLayoutManager.findFirstCompletelyVisibleItemPosition();//第一个完全可以显示
                lastItem = mGridLayoutManager.findLastCompletelyVisibleItemPosition();//最后一个完全可见
                if(firstItem==0 || firstItem==mRecyclerView.NO_POSITION){
                    //如果第一个Item=0， 或是不可见
                    lastItem = mGridLayoutManager.findLastVisibleItemPosition();

                }
            }
            //何时上拉加载更多

            if (mSwipeRefreshLayout.isEnabled()){
                      mSwipeRefreshLayout.setEnabled(true);
            }else {
                mSwipeRefreshLayout.setEnabled(false);
            }
            //1.加载更多是false
            //2.totalcount-1 === lastcount;
            //3.mSwipeRefreshLayout可以使用
            //4.不是下拉刷新状态
            //5.偏移量 dy dx 大于0 ;
            if(!isLoadMore
                    && mSwipeRefreshLayout.isEnabled()
                    && !isReFresh
                    && totalcount ==lastItem
                    && (dx>0 || dy>0)){
                   isLoadMore =true;
                  LoadMoreData();
            }
        }
    }
        //加载更多
    private void LoadMoreData() {
        if(mOnPullLoadMoreListener!=null){
            mOnPullLoadMoreListener.loadmore();
            mFootView.animate().translationY(0).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            mFootView.setVisibility(View.VISIBLE);
                            mAnimationDrawable.start();

                        }
                    }).start();
            invalidate();
            mOnPullLoadMoreListener.loadmore();
        }


    }
    //判断是否刷新完成
    public void setRefreshCompleted(){
        isReFresh   = false;
        //是否正在刷新
        setRefreshing(false);
    }
  //设置是否正在刷新
    private void setRefreshing(final boolean mRefreshing) {
         mSwipeRefreshLayout.post(new Runnable() {
             @Override
             public void run() {
               mSwipeRefreshLayout.setRefreshing(mRefreshing);
             }
         });
    }

    public void SetLoadMoreCompleted(){
        isLoadMore = false;
        isReFresh =false;
        setRefreshing(false);
        mFootView.animate().translationY(mFootView.getHeight()).
                setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(300)
                .start();

    }

    //外部设置recyclerView 的列
    public void SetGridLayout(int spanCount){
        GridLayoutManager manager = new GridLayoutManager(mContext,spanCount);
        manager.setSpanCount(spanCount);
        manager.setOrientation(LinearLayoutManager.VERTICAL); //设置网格垂直
        mRecyclerView.setLayoutManager(manager);
    }

    public void  SetAdapter(RecyclerView.Adapter adapter){
        if(adapter==null){
            mRecyclerView.setAdapter(adapter);
        }

    }
    public interface OnPullLoadMoreListener{
        void refresh();
        void loadmore();
    }
    public  void setOnPullLoadMoreListener(OnPullLoadMoreListener listener){
        mOnPullLoadMoreListener =listener;


    }
}
