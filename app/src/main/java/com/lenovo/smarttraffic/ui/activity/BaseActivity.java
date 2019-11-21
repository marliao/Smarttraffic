package com.lenovo.smarttraffic.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */

public abstract class BaseActivity extends SupportActivity {

    private static final String TAG = "BaseActivity";
    private Unbinder unbind;

    /**
     * 初始化 Toolbar
     */
    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle("");
        TextView viewById = toolbar.findViewById(R.id.tv_title);
        viewById.setText(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        unbind = ButterKnife.bind(this);
        App.getInstance().addActivity(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressedSupport() {
        //fragment逐个退出
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressedSupport();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressedSupport();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeActivity(this);
        unbind.unbind();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected abstract int getLayout();
}
