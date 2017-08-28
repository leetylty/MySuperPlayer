package com.example.administrator.mysuperplayer.util;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.example.administrator.mysuperplayer.R;

/**
 * Created by Administrator on 2017/8/23.
 */

public class ImageUtil {

    public static final float VER_POSTER_RATIO = 0.73f;
    public static final float HOR_POSTER_RATIO = 1.5f;

    public static  void  disPlayImage(ImageView imageView, String url){

            if (imageView != null && url != null) {
                Glide.with(imageView.getContext()).load(url).into(imageView);
            }
    }


    public static  void  disPlayImage(ImageView imageView, String url ,int width ,int hight){
        if (imageView!=null && url!= null && width>0 && hight>0){
             if (width>hight){
                 Glide.with(imageView.getContext())
                         .load(url)//加载图片资源
                         .diskCacheStrategy(DiskCacheStrategy.ALL)//使用缓存
                         .error(R.drawable.ic_loading_hor)//出错时默认的图片
                         .fitCenter() //设置居中
                         .override(hight,width)//重写宽高
                         .into(imageView);//加载到view
             }else {
                 Glide.with(imageView.getContext())
                         .load(url)//加载图片资源
                         .diskCacheStrategy(DiskCacheStrategy.ALL)//使用缓存
                         .error(R.drawable.ic_loading_hor)//出错时默认的图片
                         .centerCrop()//设置居中
                         .override(width,hight)//重写宽高
                         .into(imageView);//加载到view
             }
        }
    }

    public  static Point getVerPotSize (Context context, int columns){

        int width = getScreenWidth(context)/columns;
        width = (int)width - context.getResources().getDimensionPixelSize(R.dimen.dimen_8dp);
        int hight = Math.round((float)width/VER_POSTER_RATIO);
        Point point = new Point();
        point.x = width;
        point.y = hight;

        return  point;
    }
    public  static Point getHorPotSize (Context context, int columns){

        int width = getScreenWidth(context)/columns;
        width = (int)width - context.getResources().getDimensionPixelSize(R.dimen.dimen_8dp);
        int hight = Math.round((float)width/HOR_POSTER_RATIO);
        Point point = new Point();
        point.x = width;
        point.y = hight;

        return  point;
    }

    private static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
       Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        return width;
    }
}
