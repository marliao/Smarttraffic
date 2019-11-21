package com.lenovo.smarttraffic.usermanger;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.PBean;
import com.lenovo.smarttraffic.ui.customerActivity.RootActivity;
import com.lenovo.smarttraffic.util.SpUtil;

import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

public class PayActivity extends RootActivity {

    private String pid;
    private TextView tv_ts;
    private ImageView iv_mag;
    private Timer timer;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SetView();
        }
    };
    private PBean.P pinfo;
    private int money;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.payactivity, this.<ViewGroup>findViewById(R.id.ll_root)));
        initView();
        initData();
        initListeners();
    }

    private void initListeners() {
        iv_mag.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tv_ts.setText(pinfo.getCarnumber() + ":支付" + money + "元");
                return true;
            }
        });
    }

    private void initData() {
        pinfo = App.Pinfo;
        money = App.Money;
        pid = getIntent().getStringExtra("pid");
        SpUtil.putBoolean(App.User.getUsername()+pid,true);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 0, 5000);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    private void SetView() {
        Hashtable<EncodeHintType, Object> hashtable = new Hashtable<>();
        hashtable.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hashtable.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        try {
            BitMatrix encode = new QRCodeWriter().encode("" + System.currentTimeMillis(), BarcodeFormat.QR_CODE, 300, 300, hashtable);
            int[] ints = new int[300 * 300];
            for (int i = 0; i < 300; i++) {
                for (int i1 = 0; i1 < 300; i1++) {
                    if (encode.get(i, i1)) {
                        ints[i * 300 + i1] = Color.BLACK;
                    } else {
                        ints[i * 300 + i1] = Color.WHITE;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.RGB_565);
            bitmap.setPixels(ints, 0, 300, 0, 0, 300, 300);
            iv_mag.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        tv_ts = (TextView) findViewById(R.id.tv_ts);
        iv_mag = (ImageView) findViewById(R.id.iv_mag);
    }
}
