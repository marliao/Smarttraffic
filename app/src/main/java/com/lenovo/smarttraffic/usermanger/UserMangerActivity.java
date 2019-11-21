package com.lenovo.smarttraffic.usermanger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.UBean;
import com.lenovo.smarttraffic.ui.customerActivity.RootActivity;
import com.lenovo.smarttraffic.util.FileUtil;
import com.lenovo.smarttraffic.util.OrmHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMangerActivity extends RootActivity {
    private ListView my_lv;
    private List<UBean.U> uList;
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
        UBean u = (UBean) FileUtil.getFileCalss("U", UBean.class);
        try {
            dao = OrmHelper.getInstance().getDao(UBean.U.class);
            uList = u.getROWS_DETAIL();
            us = dao.queryForAll();
            SetView();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void SetView() {
        if (myAdapter == null) {
            myAdapter = new MyAdapter();
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
            Log.e("TAG",uList.size()+"");
            return uList.size();
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
            UBean.U u = uList.get(position);
            View inflate = View.inflate(UserMangerActivity.this, R.layout.item_1, null);
            ViewHolder viewHolder = new ViewHolder(inflate);
            viewHolder.tv1.setText("用户名：" + u.getUsername());
            viewHolder.tv2.setText("姓名：" + u.getPname());
            viewHolder.tv3.setText("电话：" + u.getPtel());
            for (int i = 0; i < us.size(); i++) {
                if (us.get(i).getUsername().equals(u.getUsername())) {
                    viewHolder.tv_sc.setText("已收藏");
                }
            }
            if (u.getPsex().equals("男")) {
                viewHolder.iv_tx.setImageResource(R.mipmap.touxiang_2);
            } else {
                viewHolder.iv_tx.setImageResource(R.mipmap.touxiang_1);
            }
            viewHolder.tv_sc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewHolder.tv_sc.getText().toString().equals("已收藏")) {
                        App.showToast("已收藏");
                    } else {
                        try {
                            u.setWigth(0);
                            u.setLiketime();
                            dao.create(u);
                            App.showToast("收藏成功");
                            initdata();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            if (u.getUsername().equals("user1") | u.getUsername().equals("user2")) {
                viewHolder.tv5.setText("一般管理员");
            }
            viewHolder.tv6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserMangerActivity.this, UserScrActivity.class);
                    intent.putExtra("title", "用户收藏");
                    startActivity(intent);
                }
            });
            viewHolder.iv_tx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.User = u;
                    Intent intent = new Intent(UserMangerActivity.this, UserInfoActivity.class);
                    intent.putExtra("title", "违章详情");
                    startActivity(intent);
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
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initdata();
    }
}
