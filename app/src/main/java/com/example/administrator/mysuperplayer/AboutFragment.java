package com.example.administrator.mysuperplayer;

import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.example.administrator.mysuperplayer.base.BaseFragment;

/**
 * Created by Administrator on 2017/8/16.
 */

public class AboutFragment extends BaseFragment {
     TextView textView;

    @Override
    protected int getLayoutId() {
      return  R.layout.about_fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
      textView =    bindView(R.id.about_description);
        textView.setAutoLinkMask(Linkify.ALL);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
