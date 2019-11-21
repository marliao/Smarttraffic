package com.lenovo.smarttraffic.ui.lzz;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.CBean;
import com.lenovo.smarttraffic.bean.SettingHistory;
import com.lenovo.smarttraffic.ui.customerActivity.RootActivity;
import com.lenovo.smarttraffic.util.FileUtil;
import com.lenovo.smarttraffic.util.OrmHelper;
import com.lenovo.smarttraffic.util.SpUtil;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ETCCarSettingActivity extends RootActivity {

    private TextView tv_account;
    private Spinner spinner_car_number;
    private TextView tv_search;
    private TextView tv_search_success;
    private Spinner spinner_money;
    private TextView tv_setting;
    private TextView tv_settign_money;
    private Spinner spinner_seting_history_car_number;
    private RadioButton rb_1;
    private TextView tv_searc_history;
    private ListView lv_car_histroy;
    private List<Integer> carAccountlist;
    private Dao<SettingHistory, ?> dao;
    private MyAdapter myAdapter;
    private List<SettingHistory> settinghistorylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.activity_etccar_setting, findViewById(R.id.ll_root)));
        initView();
        try {
            setTitle("ETC账户");
            App.showDialog(this, null);
            App.disDialog(this, null);
            carAccountlist = App.carAccountlist;
            if (carAccountlist.size() == 0) {
                for (int i = 0; i < 4; i++) {
                    carAccountlist.add(50);
                    carAccountlist.add(50);
                    carAccountlist.add(50);
                    carAccountlist.add(50);
                }
            }
            String carNumber = (String) spinner_car_number.getSelectedItem();
            tv_account.setText(carAccountlist.get(Integer.parseInt(carNumber) - 1) + "元");
            dao = OrmHelper.getInstance().getDao(SettingHistory.class);
            initlistener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdaspter() {
        myAdapter = new MyAdapter();
        lv_car_histroy.setAdapter(myAdapter);
    }

    public class MyAdapter extends BaseAdapter {

        private ViewHolder viewHolder;

        @Override
        public int getCount() {
            return settinghistorylist.size();
        }

        @Override
        public SettingHistory getItem(int i) {
            return settinghistorylist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(ETCCarSettingActivity.this, R.layout.item_jilu, null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            SettingHistory item = getItem(i);
            viewHolder.tv_number.setText(i + 1 + "");
            viewHolder.tv_carid.setText(item.getCarId() + "");
            viewHolder.tv_recharge_money.setText(item.getMoney() + "");
            viewHolder.tv_recharge_user.setText(item.getUsername());
            viewHolder.tv_recharge_time.setText(item.getTime());
            return view;
        }

        public class ViewHolder {
            public View rootView;
            public TextView tv_number;
            public TextView tv_carid;
            public TextView tv_recharge_money;
            public TextView tv_recharge_user;
            public TextView tv_recharge_time;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tv_number = (TextView) rootView.findViewById(R.id.tv_number);
                this.tv_carid = (TextView) rootView.findViewById(R.id.tv_carid);
                this.tv_recharge_money = (TextView) rootView.findViewById(R.id.tv_recharge_money);
                this.tv_recharge_user = (TextView) rootView.findViewById(R.id.tv_recharge_user);
                this.tv_recharge_time = (TextView) rootView.findViewById(R.id.tv_recharge_time);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.carAccountlist = carAccountlist;
    }

    private void initlistener() {
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String carNumber = (String) spinner_car_number.getSelectedItem();
                tv_account.setText(carAccountlist.get(Integer.parseInt(carNumber) - 1) + "元");
                tv_search_success.setVisibility(View.VISIBLE);
            }
        });
        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String carNumber = (String) spinner_car_number.getSelectedItem();
                String carmoney = (String) spinner_money.getSelectedItem();
                int carid = Integer.parseInt(carNumber);
                carAccountlist.set(carid - 1, carAccountlist.get(carid - 1) + Integer.parseInt(carmoney));
                tv_account.setText(carAccountlist.get(carid - 1) + "元");
                tv_settign_money.setVisibility(View.VISIBLE);
                saveInfo(carid, Integer.parseInt(carmoney)
                );
            }
        });
        tv_searc_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String carNumber = (String) spinner_car_number.getSelectedItem();
                    int carid = Integer.parseInt(carNumber);
                    settinghistorylist = dao.queryBuilder().where().eq("carId", carid).query();
                    if (!rb_1.isChecked()) {
                        Collections.reverse(settinghistorylist);
                    }
                    initAdaspter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveInfo(int carnumber, int carmoney) {
        try {
            CBean cBean = (CBean) FileUtil.getFileCalss("C", CBean.class);
            List<CBean.C> allCar = cBean.getROWS_DETAIL();
            SettingHistory settingHistory = new SettingHistory();
            settingHistory.setMoney(carmoney);
            settingHistory.setUsername(SpUtil.getString(SpUtil.USERNAME, "user1"));
            String format = new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date(System.currentTimeMillis()));
            settingHistory.setTime(format);
            settingHistory.setCarId(carnumber);
            for (CBean.C car : allCar) {
                if (car.getNumber() == carnumber) {
                    settingHistory.setCarnumber(car.getCarnumber());
                }
            }
            dao.create(settingHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        tv_account = (TextView) findViewById(R.id.tv_account);
        spinner_car_number = (Spinner) findViewById(R.id.spinner_car_number);
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search_success = (TextView) findViewById(R.id.tv_search_success);
        spinner_money = (Spinner) findViewById(R.id.spinner_money);
        tv_setting = (TextView) findViewById(R.id.tv_setting);
        tv_settign_money = (TextView) findViewById(R.id.tv_settign_money);
        spinner_seting_history_car_number = (Spinner) findViewById(R.id.spinner_seting_history_car_number);
        rb_1 = (RadioButton) findViewById(R.id.rb_1);
        tv_searc_history = (TextView) findViewById(R.id.tv_searc_history);
        lv_car_histroy = (ListView) findViewById(R.id.lv_car_histroy);
    }
}
