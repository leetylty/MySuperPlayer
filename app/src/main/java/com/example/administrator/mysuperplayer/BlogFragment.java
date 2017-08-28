package com.example.administrator.mysuperplayer;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.administrator.mysuperplayer.base.BaseFragment;

/**
 * Created by Administrator on 2017/8/16.
 */

public class BlogFragment extends BaseFragment {
     protected WebView mWebView;
     private ProgressBar mProgressBar;
     public static final int PROGRESS_MAX=100;
     public static final String URL="http://www.taijiquanpingtai.com/";

    @Override
    protected int getLayoutId() {
      return  R.layout.blog_fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mWebView =  bindView(R.id.webview);
        mProgressBar= bindView(R.id.progress_bar);
        WebSettings settings = mWebView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        mProgressBar.setMax(100);
        mWebView.loadUrl(URL);
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(mWebChromeClient );


    }
    private WebViewClient mWebViewClient = new WebViewClient(){
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            mWebView.loadUrl(URL);
            return true;
        }

    };

    private WebChromeClient mWebChromeClient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
            if(newProgress == PROGRESS_MAX){
                mProgressBar.setVisibility(View.GONE);
            }

            super.onProgressChanged(view, newProgress);
        }
    };


}
