package com.lenovo.smarttraffic.ui.customerActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.SubLineBean;
import com.lenovo.smarttraffic.bean.SubLineListBean;
import com.lenovo.smarttraffic.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class SubActivity extends RootActivity {

    private GridView gv_list;
    private TextView tv_start_place;
    private TextView tv_end_place;
    private TextView tv_query;
    private ArrayList<String> titles;
    private List<SubLineListBean> lineListBeans;
    private ArrayList<Integer> drawables;
    private CustomerAdapter customerAdapter;
    private List<String> sites;
    private List<String> transfersites;
    private AlertDialog alertDialog1;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.activity_sub, this.<ViewGroup>findViewById(R.id.ll_root)));
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        gv_list = (GridView) findViewById(R.id.gv_list);
        tv_start_place = (TextView) findViewById(R.id.tv_start_place);
        tv_end_place = (TextView) findViewById(R.id.tv_end_place);
        tv_query = (TextView) findViewById(R.id.tv_query);
    }

    private void initData() {
        SubLineBean sub = (SubLineBean) FileUtil.getFileCalss("sub", SubLineBean.class);
        lineListBeans = sub.getROWS_DETAIL();
        drawables = new ArrayList<>();
        titles = new ArrayList<>();
        titles.add("1");
        titles.add("2");
        titles.add("3");
        titles.add("4");
        titles.add("10");
        titles.add("S1");
        titles.add("S3");
        titles.add("S8");
        drawables.add(R.drawable.yuan_1);
        drawables.add(R.drawable.yuan_2);
        drawables.add(R.drawable.yuan_3);
        drawables.add(R.drawable.yuan_4);
        drawables.add(R.drawable.yuan_5);
        drawables.add(R.drawable.yuan_6);
        drawables.add(R.drawable.yuan_7);
        drawables.add(R.drawable.yuan_8);

        customerAdapter = new CustomerAdapter();
        gv_list.setAdapter(customerAdapter);
    }

    private void initEvent() {
        tv_start_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(tv_start_place);
            }
        });

        tv_end_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(tv_end_place);
            }
        });

        tv_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tv_start_place.getText().toString().trim()) || TextUtils.isEmpty(tv_end_place.getText().toString().trim())) {
                    App.showToast("请将起始点补充完成后再试");
                    return;
                }
                Intent intent = new Intent(SubActivity.this, PlanningActivity.class);
                intent.putExtra("title", "出行建议");
                startActivity(intent);
            }
        });

        gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                App.setSubLineListBean(lineListBeans.get(position));
                Intent intent = new Intent(SubActivity.this, SubDetalActivity.class);
                intent.putExtra("title", lineListBeans.get(position).getName() + "详情");
                startActivity(intent);
            }
        });
    }

    private void openDialog(TextView textView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        alertDialog = builder.create();
        View inflate = View.inflate(this, R.layout.dialog_select_line, null);
        alertDialog.setView(inflate);
        ViewHolder viewHolder = new ViewHolder(inflate);
        viewHolder.gv_list.setAdapter(customerAdapter);
        viewHolder.gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private void initView(View inflate1) {
                tvTitle = (TextView) inflate1.findViewById(R.id.tv_title);
                rcStation = (RecyclerView) inflate1.findViewById(R.id.rc_station);
            }

            private RecyclerView rcStation;
            private TextView tvTitle;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sites = lineListBeans.get(position).getSites();
                transfersites = lineListBeans.get(position).getTransfersites();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(SubActivity.this);
                alertDialog1 = builder1.create();
                View inflate1 = View.inflate(SubActivity.this, R.layout.item_stations, null);
                alertDialog1.setView(inflate1);
                initView(inflate1);
                tvTitle.setText(customerAdapter.getItem(position) + "路图");
                rcStation.setLayoutManager(new LinearLayoutManager(SubActivity.this, OrientationHelper.HORIZONTAL, false));
                rcStation.setAdapter(new CustomerStationAdapter(textView));
                alertDialog1.show();
            }
        });
        alertDialog.show();
    }

    class ViewHolder {
        public View rootView;
        public GridView gv_list;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.gv_list = (GridView) rootView.findViewById(R.id.gv_list);
        }

    }

    class CustomerStationAdapter extends RecyclerView.Adapter<CustomerStationAdapter.ViewHolder> {

        private final TextView textView;

        public CustomerStationAdapter(TextView textView) {
            this.textView = textView;
        }

        @NonNull
        @Override
        public CustomerStationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = View.inflate(SubActivity.this, R.layout.item_station_stat, null);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomerStationAdapter.ViewHolder holder, int position) {
            holder.tv_station_name.setText(sites.get(position));

            if (position == 0) {
                holder.tv_left.setVisibility(View.GONE);
            } else {
                holder.tv_left.setVisibility(View.VISIBLE);
            }

            if (position == sites.size() - 1) {
                holder.tv_right.setVisibility(View.GONE);
            } else {
                holder.tv_right.setVisibility(View.VISIBLE);
            }

            if (transfersites.contains(sites.get(position))) {
                String line = "可换乘";
                for (SubLineListBean lineListBean : lineListBeans) {
                    if (lineListBean.getSites().contains(sites.get(position))) {
                        line += lineListBean.getName();
                    }
                }
                holder.tv_replace_station.setVisibility(View.VISIBLE);
                holder.tv_replace_station.setText(line);
            } else {
                holder.tv_replace_station.setVisibility(View.INVISIBLE);
            }

            holder.iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textView == tv_start_place) {
                        if (tv_end_place.getText().toString().trim().equals(sites.get(position))) {
                            App.showToast("起点终点不能为同一站点");
                            return;
                        }
                    }
                    if (textView == tv_end_place) {
                        if (tv_start_place.getText().toString().trim().equals(sites.get(position))) {
                            App.showToast("起点终点不能为同一站点");
                            return;
                        }
                    }

                    if (alertDialog != null) {
                        if (alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                    }

                    if (alertDialog1 != null) {
                        if (alertDialog1.isShowing()) {
                            alertDialog1.dismiss();
                        }
                    }
                    if (!TextUtils.isEmpty(tv_start_place.getText().toString().trim()) && !TextUtils.isEmpty(tv_start_place.getText().toString().trim())) {
                        tv_query.setBackgroundColor(Color.parseColor("#189CD5"));
                    }

                    textView.setText(sites.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return sites.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public View rootView;
            public TextView tv_replace_station;
            public TextView tv_left;
            public TextView tv_right;
            public ImageView iv_icon;
            public TextView tv_station_name;

            public ViewHolder(View rootView) {
                super(rootView);
                this.rootView = rootView;
                this.tv_replace_station = (TextView) rootView.findViewById(R.id.tv_replace_station);
                this.tv_left = (TextView) rootView.findViewById(R.id.tv_left);
                this.tv_right = (TextView) rootView.findViewById(R.id.tv_right);
                this.iv_icon = (ImageView) rootView.findViewById(R.id.iv_icon);
                this.tv_station_name = (TextView) rootView.findViewById(R.id.tv_station_name);
            }

        }
    }


    class CustomerAdapter extends BaseAdapter {
        private TextView tvLine;

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public String getItem(int position) {
            return titles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(SubActivity.this, R.layout.item_line, null);
            } else {
                view = convertView;
            }
            initItemView(view);
            tvLine.setText(getItem(position));
            tvLine.setBackgroundResource(drawables.get(position));
            return view;
        }

        private void initItemView(View view) {
            tvLine = (TextView) view.findViewById(R.id.tv_line);
        }
    }


}
