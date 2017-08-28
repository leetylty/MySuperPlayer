package com.example.administrator.mysuperplayer.indircator;

/**
 * Created by Administrator on 2017/8/28.
 */

public interface IPagerTitle {
   //第几个，总共有几个
    void OnSelected(int index, int  totalCount);
    void OnDisSelected(int index, int totalCount);

    /***
     *
     * @param index
     * @param totalCount
     * @param leavePercent   取值0f-1.0f (1.0表示完全离开)
     * @param isleftToright  判断是否从左到右
     */

    void OnLeave (int index, int  totalCount, float leavePercent, boolean isleftToright);

    /**
     *
     * @param index
     * @param totalCount
     * @param enterPercent    取值0f-1.0f (1.0表示完全进入)
     * @param isleftToright
     */

    void OnEnter (int index, int  totalCount, float enterPercent, boolean isleftToright);
 }
