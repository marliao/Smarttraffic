package com.lenovo.smarttraffic.usermanger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.CBean;
import com.lenovo.smarttraffic.bean.PBean;
import com.lenovo.smarttraffic.bean.TBean;
import com.lenovo.smarttraffic.bean.UBean;
import com.lenovo.smarttraffic.ui.customerActivity.RootActivity;
import com.lenovo.smarttraffic.util.FileUtil;
import com.lenovo.smarttraffic.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends RootActivity {
    private ImageView iv_tx;
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private ListView lv_xq;
    private UBean.U user;
    private List<CBean.C> cList;
    private List<PBean.P> pList;
    private List<TBean.T> tList;
    private ArrayList<PBean.P> ps;
    private ArrayList<CBean.C> cs;
    private TextView tvTs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.userinfoactivity, this.<ViewGroup>findViewById(R.id.ll_root)));
        initView();
        initAll();
        initData();
        initListeners();
    }

    private void initAll() {
        CBean c = (CBean) FileUtil.getFileCalss("C", CBean.class);
        cList = c.getROWS_DETAIL();
        PBean p = (PBean) FileUtil.getFileCalss("P", PBean.class);
        pList = p.getROWS_DETAIL();
        TBean t = (TBean) FileUtil.getFileCalss("T", TBean.class);
        tList = t.getROWS_DETAIL();
    }

    private void initListeners() {

    }

    private void initData() {
        user = App.User;
        tv_1.setText("姓名：" + user.getPname());
        tv_2.setText("性别：" + user.getPsex());
        tv_3.setText("手机号码：\n" + user.getPtel());

        if (user.getPsex().equals("男")) {
            iv_tx.setImageResource(R.mipmap.touxiang_2);
        } else {
            iv_tx.setImageResource(R.mipmap.touxiang_2);
        }
        cs = new ArrayList<>();
        cs.clear();
        for (int i = 0; i < cList.size(); i++) {
            if (cList.get(i).getPcardid().equals(user.getPcardid())) {
                cs.add(cList.get(i));
            }
        }
        if (cs.size() == 0) {
            tvTs.setVisibility(View.VISIBLE);
            tvTs.setText("该用户无车辆信息");
        }
        ps = new ArrayList<PBean.P>();
        ps.clear();
        for (int i = 0; i < pList.size(); i++) {
            for (int i1 = 0; i1 < cs.size(); i1++) {
                CBean.C c = cs.get(i1);
                if (pList.get(i).getCarnumber().equals(c.getCarnumber())) {
                    ps.add(pList.get(i));
                }
            }

        }
        if (ps.size() == 0) {
            tvTs.setVisibility(View.VISIBLE);
            tvTs.setText("恭喜你，暂无违章数据！");
        }

        SetView();
    }

    private void SetView() {
        MyAdapter myAdapter = new MyAdapter();
        lv_xq.setAdapter(myAdapter);
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return ps.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PBean.P p = ps.get(position);
            View inflate = View.inflate(UserInfoActivity.this, R.layout.item_3, null);
            ViewHolder viewHolder = new ViewHolder(inflate);
            viewHolder.tv_count.setText(position + 1 + "");
            for (int i = 0; i < cs.size(); i++) {
                if (cs.get(i).getCarnumber().equals(p.getCarnumber())) {
                    int mipmap = getResources().getIdentifier(cs.get(i).getCarbrand(), "mipmap", getPackageName());
                    viewHolder.iv_band.setImageResource(mipmap);
                }
            }
            viewHolder.tv_1.setText(p.getCarnumber());
            viewHolder.tv_2.setText(p.getPaddr());
            for (int i = 0; i < tList.size(); i++) {
                if (tList.get(i).getPcode().equals(p.getPcode())) {
                    TBean.T t = tList.get(i);
                    if (t.getPmoney() > 0) {
                        viewHolder.tv_5.setText("" + t.getPmoney());
                    } else {
                        viewHolder.tv_5.setText("无");
                    }
                    if (t.getPscore() > 0) {
                        viewHolder.tv_4.setText("" + t.getPscore());
                    } else {
                        viewHolder.tv_4.setText("无");
                    }
                    viewHolder.tv_3.setText(t.getPremarks().substring(0, 2) + "..." + t.getPremarks().substring(t.getPremarks().length() - 3, t.getPremarks().length()));

                }
            }

            viewHolder.tv_6.setText(p.getPdatetime().split(" ")[0] + "\n" + p.getPdatetime().split(" ")[1]);

            if (SpUtil.getBoolean(App.User.getUsername()+"p" + p.getId(), false)) {
                viewHolder.tv_7.setText("已处理");
                viewHolder.tv_7.setTextColor(getResources().getColor(R.color.Green));
            } else {
                viewHolder.tv_7.setText("未处理");
                if (viewHolder.tv_5.getText().toString().equals("无")) {
                    viewHolder.tv_7.setTextColor(getResources().getColor(R.color.Grey));
                } else {
                    viewHolder.tv_7.setTextColor(getResources().getColor(R.color.Red));
                }
            }
            viewHolder.tv_7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = viewHolder.tv_7.getText().toString();
                    String s1 = viewHolder.tv_5.getText().toString();
                    if (s.equals("已处理") | s1.equals("无")) {
                        return;
                    }
                    App.Pinfo = p;
                    App.Money = Integer.parseInt(s1);
                    Intent intent = new Intent(UserInfoActivity.this, PayActivity.class);
                    intent.putExtra("title", "违章支付");
                    intent.putExtra("pid", "p" + p.getId());
                    startActivity(intent);
                }
            });

            return inflate;
        }

        public class ViewHolder {
            public View rootView;
            public TextView tv_count;
            public ImageView iv_band;
            public TextView tv_1;
            public TextView tv_2;
            public TextView tv_3;
            public TextView tv_4;
            public TextView tv_5;
            public TextView tv_6;
            public TextView tv_7;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tv_count = (TextView) rootView.findViewById(R.id.tv_count);
                this.iv_band = (ImageView) rootView.findViewById(R.id.iv_band);
                this.tv_1 = (TextView) rootView.findViewById(R.id.tv_1);
                this.tv_2 = (TextView) rootView.findViewById(R.id.tv_2);
                this.tv_3 = (TextView) rootView.findViewById(R.id.tv_3);
                this.tv_4 = (TextView) rootView.findViewById(R.id.tv_4);
                this.tv_5 = (TextView) rootView.findViewById(R.id.tv_5);
                this.tv_6 = (TextView) rootView.findViewById(R.id.tv_6);
                this.tv_7 = (TextView) rootView.findViewById(R.id.tv_7);
            }

        }
    }

    private void initView() {
        iv_tx = (ImageView) findViewById(R.id.iv_tx);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        lv_xq = (ListView) findViewById(R.id.lv_xq);
        tvTs = (TextView) findViewById(R.id.tv_ts);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
