package com.example.administrator.mysuperplayer;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/17.
 */

public class HomePicAdapter extends PagerAdapter {
     private Context mContext;
     private TextView textView;
     private ImageView imageView;
     private View view;
     private int[] DescStr = new int[]{
             R.string.a_name,
             R.string.b_name,
             R.string.c_name,
             R.string.d_name,
             R.string.e_name,
     };

    private int[] DescDrable = new int[]{
            R.drawable.yang1,
            R.drawable.yang2,
            R.drawable.yang3,
            R.drawable.yang4,
            R.drawable.yang5,
    };

    public HomePicAdapter(Activity activity) {
        mContext=activity;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
          view = LayoutInflater.from(mContext).inflate(R.layout.home_pic_item,null);
        textView = (TextView) view.findViewById(R.id.tv_desc);
        imageView= (ImageView) view.findViewById(R.id.iv_image);

        textView.setText(DescStr[position]);
        imageView.setImageResource(DescDrable[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
