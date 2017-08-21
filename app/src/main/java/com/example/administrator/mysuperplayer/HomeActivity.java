package com.example.administrator.mysuperplayer;


import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.administrator.mysuperplayer.base.BaseActivity;

public class HomeActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private FragmentManager mFragmentManager;
    //列表项
    private MenuItem mMenuItem;
    private Fragment mCurrentFragment;


    @Override
    protected int getViewId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        setSupportActionBar();
        setSupportActionBarIcon(R.drawable.ic_drawer_home);
        setTitle("首页");

       mNavigationView = bindView(R.id.navigation_view);
        mDrawerLayout= bindView(R.id.drawer_layout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        mMenuItem =mNavigationView.getMenu().getItem(0);
        mMenuItem.setEnabled(true);//处于选中状态
        initFragment();
        handleNavitionView();


    }

    private void initFragment() {
        mFragmentManager=getSupportFragmentManager();
        mCurrentFragment=FragmentManagerWrapper.getInstance().createFragment(HomeFragment.class);
        mFragmentManager.beginTransaction().add(R.id.fl_main_content,mCurrentFragment).commit();


    }

    //对列表项进行监听
    private void handleNavitionView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(mMenuItem!=null){
                    mMenuItem.setCheckable(false);
                }
                  switch (item.getItemId()){
                      case R.id.navigation_item_video:
                          switchFragment(HomeFragment.class);
                          mToolbar.setTitle("首页");
                          break;
                      case R.id.navigation_item_blog:
                          switchFragment(BlogFragment.class);
                          mToolbar.setTitle("博客");
                          break;
                      case R.id.navigation_item_about:
                         switchFragment(AboutFragment.class);
                         mToolbar.setTitle("关于");
                          break;


                  }
                mMenuItem = item;
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                item.setChecked(true);
                item.setCheckable(true);
                return false;
            }
        });

    }

    @Override
    protected void initData() {

    }

    /*@Override
    public void onBackPressed() {
        mFragmentManager = getSupportFragmentManager();
       mCurrentFragment = mFragmentManager.findFragmentById(R.id.fl_main_content);
        if(mCurrentFragment!=null ){
            mCurrentFragment.onDestroy();
        }

        super.onBackPressed();
    }*/

    private void switchFragment(Class<?> clazz) {
        Fragment fragment = FragmentManagerWrapper.getInstance().createFragment(clazz);
        if (fragment.isAdded()) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment).commitAllowingStateLoss();
        } else {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.fl_main_content, fragment).commitAllowingStateLoss();
        }
        mCurrentFragment = fragment;
    }


}
