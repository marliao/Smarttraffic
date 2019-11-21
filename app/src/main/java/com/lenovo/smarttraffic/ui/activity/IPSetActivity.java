package com.lenovo.smarttraffic.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.ui.customerActivity.RootActivity;
import com.lenovo.smarttraffic.util.SpUtil;

public class IPSetActivity extends RootActivity implements View.OnClickListener {


    private EditText et_1;
    private EditText et_2;
    private EditText et_3;
    private EditText et_4;
    private Button bt_qd;
    private String[] split;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.ipset, this.<ViewGroup>findViewById(R.id.ll_root)));
        initView();
        initData();
        initListeners();
    }

    private void initListeners() {
        et_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = s.toString();
                if (!TextUtils.isEmpty(s1)) {
                    int i = Integer.parseInt(s1);
                    if (i > 255) {
                        App.showToast("您输入的有误，请重新输入");
                        et_1.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = s.toString();
                if (!TextUtils.isEmpty(s1)) {
                    int i = Integer.parseInt(s1);
                    if (i > 255) {
                        App.showToast("您输入的有误，请重新输入");
                        et_2.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = s.toString();
                if (!TextUtils.isEmpty(s1)) {
                    int i = Integer.parseInt(s1);
                    if (i > 255) {
                        App.showToast("您输入的有误，请重新输入");
                        et_3.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = s.toString();
                if (!TextUtils.isEmpty(s1)) {
                    int i = Integer.parseInt(s1);
                    if (i > 255) {
                        App.showToast("您输入的有误，请重新输入");
                        et_4.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initData() {
        String ip = SpUtil.getString("ip", "192.168.1.101");
        split = ip.split("\\.");
        et_1.setText(split[0]);
        et_2.setText(split[1]);
        et_3.setText(split[2]);
        et_4.setText(split[3]);
    }

    private void initView() {
        et_1 = (EditText) findViewById(R.id.et_1);
        et_2 = (EditText) findViewById(R.id.et_2);
        et_3 = (EditText) findViewById(R.id.et_3);
        et_4 = (EditText) findViewById(R.id.et_4);
        bt_qd = (Button) findViewById(R.id.bt_qd);

        bt_qd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_qd:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String et1 = et_1.getText().toString().trim();
        if (TextUtils.isEmpty(et1)) {
            Toast.makeText(this, "ip不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String et2 = et_2.getText().toString().trim();
        if (TextUtils.isEmpty(et2)) {
            Toast.makeText(this, "ip不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String et3 = et_3.getText().toString().trim();
        if (TextUtils.isEmpty(et3)) {
            Toast.makeText(this, "ip不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String et4 = et_4.getText().toString().trim();
        if (TextUtils.isEmpty(et4)) {
            Toast.makeText(this, "ip不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] strings = {et1, et2, et3, et4};
        for (int i = 0; i < strings.length; i++) {
            if (Integer.parseInt(strings[i]) > 255) {
                App.showToast("您的IP错误");
                return;
            }
        }
        SpUtil.putString("ip", et1 + "." + et2 + "." + et3 + "." + et4);
        App.showToast("设置成功");
        finish();
    }
}
