package com.example.administrator.mysuperplayer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mysuperplayer.base.BaseFragment;
import com.example.administrator.mysuperplayer.model.Channel;
import com.hejunlin.superindicatorlibray.CircleIndicator;
import com.hejunlin.superindicatorlibray.LoopViewPager;

/**
 * Created by Administrator on 2017/8/16.
 */

public class HomeFragment extends BaseFragment {
    private LoopViewPager mLoopviewPager;
    private CircleIndicator indicator;
    private GridView mGridView;
    public  static String TAG = "HomeFragment";

    @Override
    protected int getLayoutId() {
      return  R.layout.home_fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
         mLoopviewPager=  bindView(R.id.loopviewpager);
        indicator =bindView(R.id.indicator);
        mLoopviewPager.setAdapter(new HomePicAdapter(getActivity()));
        mLoopviewPager.setLooperPic(true);//5秒自动轮询
        indicator.setViewPager(mLoopviewPager);
        mGridView = bindView(R.id.gv_channel);
        mGridView.setAdapter(new ChannelAdapter());
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG,">>-- OnItemClick"+ i);
                switch (i){
                    case 6: //直播

                        break;
                    case 7: //收藏

                        break;
                    case 8: //历史

                        break;

                    default:
                        DetailListActivity.LaunchDetailListActivity(getActivity(),i+1);
                        break;
                }
            }
        });


    }

    class ChannelAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Channel.MAX_COUNT;
        }

        @Override
        public Channel getItem(int position) {
            return new Channel(position + 1, getActivity());
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Channel chanel = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.home_grid_item, null);
                holder = new ViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.home_item_tv);
                holder.imageView = (ImageView) convertView.findViewById(R.id.home_item_img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(chanel.getChanneName());
            int id = chanel.getChannelId();
            int imgResId = -1;
            switch (id) {
                case Channel.SHOW:
                    imgResId = R.drawable.ic_show;
                    break;
                case Channel.MOVIE:
                    imgResId = R.drawable.ic_movie;
                    break;
                case Channel.COMIC:
                    imgResId = R.drawable.ic_comic;
                    break;
                case Channel.DOCUMENTRY:
                    imgResId = R.drawable.ic_movie;
                    break;
                case Channel.MUSIC:
                    imgResId = R.drawable.ic_music;
                    break;
                case Channel.VARITEM:
                    imgResId = R.drawable.ic_variety;
                    break;
                case Channel.LIVE:
                    imgResId = R.drawable.ic_live;
                    break;
                case Channel.FAVOURITE:
                    imgResId = R.drawable.ic_bookmark;
                    break;
                case Channel.HISTORY:
                    imgResId = R.drawable.ic_history;
                    break;
            }

            holder.imageView.setImageDrawable(getActivity().getResources().getDrawable(imgResId));

            return convertView;
        }
    }

    class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
