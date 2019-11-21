package com.lenovo.smarttraffic.ui.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class FirstFragment extends BaseFragment {


    @Override
    protected View getSuccessView() {
        TextView textView = new TextView(getActivity());
        textView.setText("第一页");
        return textView;
    }

    @Override
    protected Object requestData() {
        SystemClock.sleep(1000);/*模拟请求服务器的延时过程*/
        return "";/*加载成功*/
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 类似于 Activity的 onNewIntent()
     */
    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);

        Toast.makeText(_mActivity, args.getString("from"), Toast.LENGTH_SHORT).show();
    }
}
