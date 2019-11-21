package com.lenovo.smarttraffic.ui.customerActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.util.HttpUtil;
import com.lenovo.smarttraffic.util.ZoomUtil;

public class PictureActivity extends RootActivity {
    private ImageView iv_image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.activity_picutre, this.<ViewGroup>findViewById(R.id.ll_root)));
        initView();
        initData();
    }

    private void initView() {
        iv_image = (ImageView) findViewById(R.id.iv_image);
    }

    private void initData() {
        String img = getIntent().getStringExtra("img");
        HttpUtil.getImage(img, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                iv_image.setImageBitmap(bitmap);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                iv_image.setImageResource(R.mipmap.loading);
            }
        });
        iv_image.setOnTouchListener(new ZoomUtil());
    }
}
