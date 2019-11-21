package com.lenovo.smarttraffic.ui.customerActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.SubLineListBean;
import com.lenovo.smarttraffic.bean.TimeBean;
import com.lenovo.smarttraffic.util.HttpUtil;

import java.util.List;

public class SubDetalActivity extends RootActivity {
    private GridView gv_list;
    private TextView tv_start_place;
    private TextView tv_start_place_start_time;
    private TextView tv_start_place_end_time;
    private TextView tv_end_place;
    private TextView tv_end_place_start_time;
    private TextView tv_end_place_end_time;
    private TextView tv_money;
    private ImageView iv_image;
    private SubLineListBean subLineListBean;
    private List<String> sites;
    private CustomerAdapter customerAdapter;
    private TextView tv_count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.activity_subdetail, this.<ViewGroup>findViewById(R.id.ll_root)));
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        gv_list = (GridView) findViewById(R.id.gv_list);
        tv_start_place = (TextView) findViewById(R.id.tv_start_place);
        tv_start_place_start_time = (TextView) findViewById(R.id.tv_start_place_start_time);
        tv_start_place_end_time = (TextView) findViewById(R.id.tv_start_place_end_time);
        tv_end_place = (TextView) findViewById(R.id.tv_end_place);
        tv_end_place_start_time = (TextView) findViewById(R.id.tv_end_place_start_time);
        tv_end_place_end_time = (TextView) findViewById(R.id.tv_end_place_end_time);
        tv_money = (TextView) findViewById(R.id.tv_money);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        tv_count = (TextView) findViewById(R.id.tv_count);
    }

    private void initData() {
        subLineListBean = App.getSubLineListBean();
        sites = subLineListBean.getSites();
        customerAdapter = new CustomerAdapter();
        gv_list.setAdapter(customerAdapter);
        setViewData();
    }

    private void initEvent() {
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubDetalActivity.this,PictureActivity.class);
                intent.putExtra("title", "图片查看");
                intent.putExtra("img", subLineListBean.getMap());
                startActivity(intent);
            }
        });
    }

    private void setViewData() {
        List<TimeBean> time = subLineListBean.getTime();
        tv_start_place.setText(time.get(0).getSite());
        tv_start_place_start_time.setText("首班:" + time.get(0).getStarttime());
        tv_start_place_end_time.setText("末班:" + time.get(0).getEndtime());

        tv_end_place.setText(time.get(1).getSite());
        tv_end_place_start_time.setText("首班:" + time.get(1).getStarttime());
        tv_end_place_end_time.setText("末班:" + time.get(1).getEndtime());

        tv_count.setText(sites.size() + "站/" + (sites.size() * 2 - 2) + "公里");

        HttpUtil.getImage(subLineListBean.getMap(), new Response.Listener<Bitmap>() {
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
    }

    class CustomerAdapter extends BaseAdapter {
        private TextView tvStation;

        @Override
        public int getCount() {
            return sites.size();
        }

        @Override
        public String getItem(int position) {
            return sites.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(SubDetalActivity.this, R.layout.item_station, null);
            } else {
                view = convertView;
            }
            initItemView(view);
            tvStation.setText(getItem(position));
            return view;
        }

        private void initItemView(View view) {
            tvStation = (TextView) view.findViewById(R.id.tv_station);
        }
    }

}
