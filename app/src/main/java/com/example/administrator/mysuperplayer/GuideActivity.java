package com.example.administrator.mysuperplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class GuideActivity extends Activity  implements ViewPager.OnPageChangeListener{

    private List<View> mGuideView;
    private ViewPager mViewPager;
    private LayoutInflater mInflater;
    private  Context context;
    private ImageView[] Dotpoint;
    private int lastPoint;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
         mInflater = LayoutInflater.from(this);

        initView();
        initViewpager();
        initDot();


    }
    //点集合初始化
    private void initDot() {
        LinearLayout mLinearLyaout =(LinearLayout) findViewById(R.id.dot_layout);
         Dotpoint= new ImageView[mGuideView.size()];
        for(int i=0; i<mGuideView.size();i++){
            //让布局获取 子元素， 并用集合遍历
           Dotpoint[i]= (ImageView) mLinearLyaout.getChildAt(i);
            //让其他变为灰色，0变为白色
            Dotpoint[i].setEnabled(false);
            lastPoint=0;


        }

         Dotpoint[0].setEnabled(true);

        Toast.makeText(this,"进行了初始化点集合",Toast.LENGTH_LONG).show();

    }

    ///初始化ViewPager
    private void initViewpager() {
        mViewPager=(ViewPager) findViewById(R.id.viewpager);
        MyGuideAdapter myGuideAdapter =new MyGuideAdapter(mGuideView,this);
        mViewPager.setAdapter(myGuideAdapter);
        //添加监听事件
        mViewPager.addOnPageChangeListener(this);



    }
    //初始化视图
    private void initView() {
        mGuideView = new ArrayList<>();
        mGuideView.add(mInflater.inflate(R.layout.guide1,null));
        mGuideView.add(mInflater.inflate(R.layout.guide2,null));
        mGuideView.add(mInflater.inflate(R.layout.guide3,null));
          mViewPager= (ViewPager) findViewById(R.id.viewpager);
    }



    public class MyGuideAdapter extends PagerAdapter{


      public MyGuideAdapter(List<View> mList, Context mContext) {
          super();
          mGuideView= mList;
          context= mContext;

      }

      @Override
      public int getCount() {
          return mGuideView.size();
      }
      //实例化ViewPager每个页面
      @Override
      public Object instantiateItem(ViewGroup container, int position) {
             if(mGuideView!=null && mGuideView.size()>=0){
                 container.addView(mGuideView.get(position));
                 if(position == (mGuideView.size() - 1)){
                     ImageView mIcstart= (ImageView) mGuideView.get(position).findViewById(R.id.ic_start);
                     mIcstart.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             startHomeActivity();
                             //更改第一次进入的状态
                             getDuied();
                         }
                     });

                 }
                 return mGuideView.get(position);
             }else{
                 return null;
             }



      }

      @Override
      public void destroyItem(ViewGroup container, int position, Object object) {
          if(mGuideView!=null && mGuideView.size()>=0){
             container.removeView(mGuideView.get(position));
          }

      }

      @Override
      public boolean isViewFromObject(View view, Object object) {
          return view==object;
      }
  }
    //更改第一次进入状态
    private void getDuied() {
       SharedPreferences sp = getSharedPreferences("config",MODE_PRIVATE);
        sp.edit().putBoolean("isFirstIn",false).commit();


    }

    //去主界面
    private void startHomeActivity() {
        Intent intent = new Intent(GuideActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();


    }
    //滑动监听

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    //滑动过程中的回调，需要把之前的点变为灰色，滑动完成后调用
    @Override
    public void onPageSelected(int position) {

        setCurrtDot(position);


    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Toast.makeText(this,"开始滑动",Toast.LENGTH_LONG).show();

    }

    private void setCurrtDot(int state) {
        Toast.makeText(this,"进行了滑动判断",Toast.LENGTH_LONG).show();
      //接收到的显示成白色
      Dotpoint[state].setEnabled(true);
        //上一个变灰色
        Dotpoint[lastPoint].setEnabled(false);
        //如果这个点继续滑动，变白
        lastPoint=state;

    }
}
//1,进行布局的构建，Viewpager+LinearLayout 包裹三个圆点。
//2，创建引导页的三个子布局
//3. 实例化各种组件 ，布局添加到 View中
//4，adapter进行实例化
//5. 进行vierpager 滑动监听
/*6
处理点集合 效果和  滑动
        1，首先建立点数组，找到副布局。
        2.initDot,处理点滑动逻辑
        3.给按钮添加点击事件跳到首页
        4.给引到图加入geted 状态，下次进入不再引导*/
