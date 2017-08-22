package com.example.administrator.mysuperplayer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.administrator.mysuperplayer.base.BaseActivity;
import com.example.administrator.mysuperplayer.model.Channel;
import com.example.administrator.mysuperplayer.model.Site;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/18.
 */

public class DetailListActivity extends BaseActivity {
     public  static  final  String CHANNID="channid";
     public  static  final String TAG ="DetailListActivity";
     private int mChannId;
     private ViewPager mDetailViewPager;
    @Override
    protected int getViewId() {
        return R.layout.activity_detail_list;
    }

    @Override
    protected void initView() {
        Intent intent =getIntent();
        if (intent!=null){
           mChannId = intent.getIntExtra(CHANNID,0);
        }
        Channel channel = new Channel(mChannId,this);
         String titleName = channel.getChanneName();
        setSupportActionBar();//表示允许actionBar
        setSupportArrowActionBar(true);
        setTitle(titleName);
       // Log.i(TAG,"--->>>>TitleName="+titleName);
        mDetailViewPager = bindView(R.id.detail_pager);
        mDetailViewPager.setAdapter(new SitePagerAdapter(getSupportFragmentManager(),this,mChannId));


    }

    @Override
    protected void initData() {

    }
    //处理左上角返回箭头


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    public  static  void  LaunchDetailListActivity(Context context , int channId){
        Intent intent = new Intent(context,DetailListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(CHANNID,channId);
        context.startActivity(intent);
    }


    class SitePagerAdapter extends FragmentPagerAdapter{
        private Context mContext;
        private int  mChannId;
        private HashMap<Integer,DetailListFragment> mPagerMap;
        public SitePagerAdapter(FragmentManager fm,Context context,int channId) {
            super(fm);
            mContext=context;
            mChannId=channId;
            mPagerMap = new HashMap<>();

        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = DetailListFragment.newInstance(new Site(1).getChannelId(),mChannId);
            return fragment;
        }

        @Override
        public int getCount() {
            return Site.MAX_SIZE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Object obj = super.instantiateItem(container, position);
               if(obj instanceof DetailListFragment){
                     mPagerMap.put(position,(DetailListFragment) obj);
               }
            return  obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mPagerMap.remove(position);
        }
    }
}
