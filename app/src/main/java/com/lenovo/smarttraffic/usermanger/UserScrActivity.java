package com.lenovo.smarttraffic.usermanger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.UBean;
import com.lenovo.smarttraffic.ui.customerActivity.RootActivity;
import com.lenovo.smarttraffic.util.OrmHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class UserScrActivity extends RootActivity {
    private ListView my_lv;
    private List<UBean.U> us = new ArrayList<>();
    private Dao<UBean.U, ?> dao;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.usermangeractivity, this.<ViewGroup>findViewById(R.id.ll_root)));
        initView();
        initdata();
    }

    private void initdata() {
//        UBean u = (UBean) FileUtil.getFileCalss("U", UBean.class);
        try {
            dao = OrmHelper.getInstance().getDao(UBean.U.class);
            us = dao.queryForAll();
            SetView();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void SetView() {
        Collections.sort(us, new Comparator<UBean.U>() {
            @Override
            public int compare(UBean.U o1, UBean.U o2) {
                return o2.getWigth()-o1.getWigth();
            }
        });
        if (myAdapter == null) {
            myAdapter = new  MyAdapter();
            my_lv.setAdapter(myAdapter);
        }
        myAdapter.notifyDataSetChanged();
    }

    private void initView() {
        my_lv = (ListView) findViewById(R.id.my_lv);
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return us.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            UBean.U u = us.get(position);
            View inflate = View.inflate(UserScrActivity.this, R.layout.item_2, null);
            ViewHolder viewHolder = new ViewHolder(inflate);
            viewHolder.tv1.setText("用户名：" + u.getUsername());
            viewHolder.tv2.setText("姓名：" + u.getPname());
            viewHolder.tv3.setText("电话：" + u.getPtel());
            if (u.getPsex().equals("男")) {
                viewHolder. iv_tx.setImageResource(R.mipmap.touxiang_2);
            } else {
                viewHolder.iv_tx.setImageResource(R.mipmap.touxiang_2);
            }
            if (u.getUsername().equals("user1") | u.getUsername().equals("user2")) {
                viewHolder.tv5.setText("一般管理员");
            }
            viewHolder.tv4.setText("" + u.getLiketime());
            if (u.getWigth() > 0) {
                viewHolder.tv_zd.setText("取消置顶");
                viewHolder.my_item.setBackgroundColor(getResources().getColor(R.color.Grey));
            }
            viewHolder.tv_sc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        dao.delete(u);
                        App.showToast("取消成功");
                        initdata();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            viewHolder.tv_zd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewHolder.tv_zd.getText().toString().equals("取消置顶")) {
                        u.setWigth(0);
                    } else {
                        u.setWigth(1);
                    }
                    try {
                        dao.update(u);
                        App.showToast("操作成功");
                        initdata();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

            return inflate;
        }


        public class ViewHolder {
            public View rootView;
            public ImageView iv_tx;
            public TextView tv1;
            public TextView tv2;
            public TextView tv3;
            public TextView tv4;
            public TextView tv5;
            public TextView tv6;
            public TextView tv_sc;
            public TextView tv_zd;
            public LinearLayout my_item;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.iv_tx = (ImageView) rootView.findViewById(R.id.iv_tx);
                this.tv1 = (TextView) rootView.findViewById(R.id.tv1);
                this.tv2 = (TextView) rootView.findViewById(R.id.tv2);
                this.tv3 = (TextView) rootView.findViewById(R.id.tv3);
                this.tv4 = (TextView) rootView.findViewById(R.id.tv4);
                this.tv5 = (TextView) rootView.findViewById(R.id.tv5);
                this.tv6 = (TextView) rootView.findViewById(R.id.tv6);
                this.tv_sc = (TextView) rootView.findViewById(R.id.tv_sc);
                this.tv_zd = (TextView) rootView.findViewById(R.id.tv_zd);
                this.my_item = (LinearLayout) rootView.findViewById(R.id.my_item);
            }

        }
    }
}
