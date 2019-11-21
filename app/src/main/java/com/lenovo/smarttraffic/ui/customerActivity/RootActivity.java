package com.lenovo.smarttraffic.ui.customerActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.ui.activity.BaseActivity;

public class RootActivity extends BaseActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        setTitle(getIntent().getStringExtra("title"));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_title;
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setTitle(String title) {
        initToolBar(toolbar, true, title);
    }
}
