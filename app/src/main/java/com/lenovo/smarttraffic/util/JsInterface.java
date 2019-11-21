package com.lenovo.smarttraffic.util;

import android.content.Context;
import android.provider.Settings;
import android.provider.Telephony;
import android.webkit.JavascriptInterface;

import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.ui.customerActivity.UserQianDaoActivity;

import api.SetTitle;

public class JsInterface {
    private SetTitle setTitle;

    public JsInterface(SetTitle setTitle) {
        this.setTitle = setTitle;
    }

    @JavascriptInterface
    public void notice(String msg) {
        boolean aBoolean = SpUtil.getBoolean(SpUtil.QIANDAO, false);
        if (aBoolean) {
            msg = "您已领取";
        }
        App.showToast(msg);
        SpUtil.putBoolean(SpUtil.QIANDAO, true);
    }

    @JavascriptInterface
    public void setTitle(String title) {
        setTitle.setTitle(title);
    }
}
