package com.lenovo.smarttraffic.ui.lzz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.Park;
import com.lenovo.smarttraffic.ui.customerActivity.RootActivity;

import java.text.DecimalFormat;

public class CarParkingDetailActivity extends RootActivity {

    private TextView tv_name;
    private TextView tv_addr;
    private TextView tv_distance;
    private TextView tv_empty_space;
    private TextView tv_tip;
    private Park.ROWSDETAILBean item;
    private RelativeLayout rl_addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.activity_car_parking_detail, findViewById(R.id.ll_root)));
        initView();
        setTitle("停车场详情");
        App.showDialog(this, null);
        App.disDialog(this, null);
        item = App.carparking;
        initUi();
    }

    private void initUi() {
        tv_name.setText(item.getName());
        tv_addr.setText(item.getAddress());
        tv_distance.setText(new DecimalFormat("##").format(item.getDistance()) + "米");
        tv_empty_space.setText(item.getEmptySpace() + "个/" + item.getAllSpace());
        String[] split = item.getRemarks().split("，");
        tv_tip.setText("每小时5元，" + split[1]);
        rl_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CarParkingDetailActivity.this, MapActivity.class));
            }
        });
    }

    private void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_addr = (TextView) findViewById(R.id.tv_addr);
        tv_distance = (TextView) findViewById(R.id.tv_distance);
        tv_empty_space = (TextView) findViewById(R.id.tv_empty_space);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        rl_addr = (RelativeLayout) findViewById(R.id.rl_addr);
    }
}
