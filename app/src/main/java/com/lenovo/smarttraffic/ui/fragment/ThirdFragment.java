package com.lenovo.smarttraffic.ui.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

public class ThirdFragment extends BaseFragment {




    @Override
    protected View getSuccessView() {
        TextView textView = new TextView(getActivity());
        textView.setText("第三页");
        return textView;
    }

    @Override
    protected Object requestData() {
        SystemClock.sleep(1000);/*模拟请求服务器的延时过程*/
        return null;/*加载失败*/
    }

    @Override
    public void onClick(View view) {

    }
}
