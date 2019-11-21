package com.lenovo.smarttraffic.ui.lzz;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.RoadLatlng;
import com.lenovo.smarttraffic.ui.customerActivity.RootActivity;

import java.text.DecimalFormat;
import java.util.List;

public class RoadDetailActivity extends RootActivity {

    private RoadLatlng roadLatlng;
    private TextView tv_start_position;
    private TextView tv_end_position;
    private TextView tv_time;
    private TextView tv_distance;
    private ListView lv_road_detail;
    private List<RoadLatlng.RouteBean.PathsBean.StepsBean> steps;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.activity_road_detail, findViewById(R.id.ll_root)));
        initView();
        setTitle("路线指南");
        App.showDialog(this, null);
        App.disDialog(this, null);
        roadLatlng = App.roadLatlng;
        initUi();
        intAdapter();
    }

    private void intAdapter() {
        steps = roadLatlng.getRoute().getPaths().get(0).getSteps();
        myAdapter = new MyAdapter();
        lv_road_detail.setAdapter(myAdapter);
    }

    public class MyAdapter extends BaseAdapter {

        private ViewHolder viewHolder;

        @Override
        public int getCount() {
            return steps.size();
        }

        @Override
        public RoadLatlng.RouteBean.PathsBean.StepsBean getItem(int position) {
            return steps.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(RoadDetailActivity.this, R.layout.item_road_detail, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            RoadLatlng.RouteBean.PathsBean.StepsBean item = getItem(position);
            if (position == steps.size() - 1) {
                viewHolder.iv_image.setVisibility(View.VISIBLE);
                viewHolder.iv_image.setImageResource(R.mipmap.end);
            } else if (position == 0) {
                viewHolder.iv_image.setVisibility(View.VISIBLE);
                viewHolder.iv_image.setImageResource(R.mipmap.start);
            } else {
                viewHolder.iv_image.setVisibility(View.GONE);
            }
            viewHolder.tv_action.setText("向" + item.getOrientation() + "方向行驶");
            viewHolder.tv_road.setText(item.getRoad());
            viewHolder.tv_detail.setText(item.getInstruction());
            return convertView;
        }

        public class ViewHolder {
            public View rootView;
            public ImageView iv_image;
            public TextView tv_action;
            public TextView tv_road;
            public TextView tv_detail;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.iv_image = (ImageView) rootView.findViewById(R.id.iv_image);
                this.tv_action = (TextView) rootView.findViewById(R.id.tv_action);
                this.tv_road = (TextView) rootView.findViewById(R.id.tv_road);
                this.tv_detail = (TextView) rootView.findViewById(R.id.tv_detail);
            }

        }
    }

    private void initUi() {
        tv_start_position.setText(App.startpostion);
        tv_end_position.setText(App.endpostion);
        RoadLatlng.RouteBean.PathsBean pathsBean = roadLatlng.getRoute().getPaths().get(0);
        int duration = Integer.parseInt(pathsBean.getDuration());
        tv_time.setText(duration / 60 + "分钟");
        int distance = Integer.parseInt(pathsBean.getDistance());
        String format = new DecimalFormat("##").format(distance / 1000);
        tv_distance.setText(format + "公里");
    }

    private void initView() {
        tv_start_position = (TextView) findViewById(R.id.tv_start_position);
        tv_end_position = (TextView) findViewById(R.id.tv_end_position);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_distance = (TextView) findViewById(R.id.tv_distance);
        lv_road_detail = (ListView) findViewById(R.id.lv_road_detail);
    }
}
