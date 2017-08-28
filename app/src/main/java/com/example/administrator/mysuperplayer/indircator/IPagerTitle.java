package com.example.administrator.mysuperplayer.indircator;

/**
 * Created by Administrator on 2017/8/28.
 */

public interface IPagerTitle {
   //第几个，总共有几个
    void OnSelected(int index, int  totalCount);
    void OnDisSelected(int index, int totalCount);
}
