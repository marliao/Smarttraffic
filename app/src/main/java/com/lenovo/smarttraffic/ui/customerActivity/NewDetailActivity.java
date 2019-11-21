package com.lenovo.smarttraffic.ui.customerActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.NewListBean;
import com.lenovo.smarttraffic.util.SpUtil;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewDetailActivity extends RootActivity {
    private TextView tv_type;
    private TextView tv_time;
    private TextView tv_info;
    private ImageView iv_image;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat dateFormat;
    private LinearLayout ll_visi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.activity_newsdeatil, this.<ViewGroup>findViewById(R.id.ll_root)));
        initView();
        initData();
    }

    private void initView() {
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_info = (TextView) findViewById(R.id.tv_info);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        ll_visi = (LinearLayout) findViewById(R.id.ll_visi);
    }

    private void initData() {
        NewListBean newListBean = App.getNewListBean();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分");
        Date parse = simpleDateFormat.parse(newListBean.getCreatetime(), new ParsePosition(0));
        tv_type.setText("分类:" + SpUtil.getString(SpUtil.TYPE, "推荐"));
        tv_time.setText(dateFormat.format(parse));
        tv_info.setText(newListBean.getContent());

        if (TextUtils.isEmpty(newListBean.getImg())) {
            ll_visi.setVisibility(View.GONE);
        } else {
            ll_visi.setVisibility(View.VISIBLE);
            iv_image.setImageBitmap(newListBean.getImage());
        }
    }
}
