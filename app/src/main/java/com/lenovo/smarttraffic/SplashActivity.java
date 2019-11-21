package com.lenovo.smarttraffic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lenovo.smarttraffic.util.SpUtil;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashactivity);
        SpUtil.putBoolean(SpUtil.ISLOGIN, false);
        App.getHandler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 1000);
    }

}
