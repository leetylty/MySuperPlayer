package com.example.administrator.mysuperplayer.indircator;

/**
 * Created by Administrator on 2017/8/28.
 */

public interface IpagerChangeListener {

    /**
     * 页面选中
     * @param position
     */
    void OnpageSelected(int position);

    /**
     *   页面滚动回调
     * @param position  位置
     * @param positiongOffset  滚动百分比
     * @param positionOffsetPixel  距离
     */
    void OnpageScroll(int position, float positiongOffset, int positionOffsetPixel);

    /**
     *  当状态发生改变的时候 ，当静止到滑动或者当滑动到静止
     * @param position
     */
    void OnpagerScrollStateChanged(int position);


}
