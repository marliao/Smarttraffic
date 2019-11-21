package com.lenovo.smarttraffic.ui.customerActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.util.JsInterface;

import api.SetTitle;

public class UserQianDaoActivity extends RootActivity implements SetTitle {
    private ProgressBar pb_progress;
    private WebView webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.activity_userqiandao, this.<ViewGroup>findViewById(R.id.ll_root)));
        initView();
        initData();
    }

    private void initView() {
        pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
        webview = (WebView) findViewById(R.id.webview);
    }

    @SuppressLint("JavascriptInterface")
    private void initData() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    pb_progress.setProgress(newProgress);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pb_progress.setVisibility(View.GONE);
                        }
                    }, 800);
                } else {
                    pb_progress.setVisibility(View.VISIBLE);
                    pb_progress.setProgress(newProgress);
                }
            }
        });
        webview.addJavascriptInterface(new JsInterface(this), "WVJBInterface");
        webview.loadUrl("file:///android_asset/www/index.html");
    }
}
