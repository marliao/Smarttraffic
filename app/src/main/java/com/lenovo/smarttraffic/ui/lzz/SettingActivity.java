package com.lenovo.smarttraffic.ui.lzz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lenovo.smarttraffic.MainActivity;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.ui.customerActivity.RootActivity;
import com.lenovo.smarttraffic.ui.customerActivity.UserQianDaoActivity;
import com.lenovo.smarttraffic.util.SpUtil;

public class SettingActivity extends RootActivity implements View.OnClickListener {

    private TextView tv_qiandao;
    private Button btn_login;
    private TextView tv_etc_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.activity_setting, findViewById(R.id.ll_root)));
        initView();
        setTitle("设置");
        initUI();
    }

    private void initUI() {
        if (SpUtil.getBoolean(SpUtil.ISLOGIN, false)) {
            btn_login.setEnabled(true);
        } else {
            btn_login.setEnabled(false);
        }
        ;
    }

    private void initView() {
        tv_qiandao = (TextView) findViewById(R.id.tv_qiandao);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_qiandao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, UserQianDaoActivity.class);
                intent.putExtra("title", "用户签到");
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(this);
        tv_etc_setting = (TextView) findViewById(R.id.tv_etc_setting);
        tv_etc_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, ETCCarSettingActivity.class);
                intent.putExtra("title", "账户设置");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                SpUtil.putBoolean(SpUtil.ISLOGIN, false);
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
}
