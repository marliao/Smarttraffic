package com.lenovo.smarttraffic.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.lenovo.smarttraffic.App;

import org.greenrobot.greendao.InternalUnitTestDaoAccess;

public class SpUtil {
    public static final String ISLOGIN = "sdfzd";
    public static final String USERNAME = "ree";
    public static final String USERPWD = "revfe";
    public static final String USERROLE = "rre";
    public static final String QIANDAO = "qiandao";
    public static final String TYPE = "type";

    private static SharedPreferences sp;

    public static void putString(String key, String value) {
        if (sp == null) {
            sp = App.getContext().getSharedPreferences("setting", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    public static String getString(String key, String defValue) {
        if (sp == null) {
            sp = App.getContext().getSharedPreferences("setting", Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    public static void putInt(String key, int value) {
        if (sp == null) {
            sp = App.getContext().getSharedPreferences("setting", Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(String key, int defValue) {
        if (sp == null) {
            sp = App.getContext().getSharedPreferences("setting", Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        if (sp == null) {
            sp = App.getContext().getSharedPreferences("setting", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        if (sp == null) {
            sp = App.getContext().getSharedPreferences("setting", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }
}
