package com.lenovo.smarttraffic.ui.customerActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.LabelBean;
import com.lenovo.smarttraffic.util.DraggedGridView;

import java.util.ArrayList;
import java.util.List;

import api.Result;
import api.Status;

public class AddLabelActivity extends RootActivity implements Status {
    public static Result<LabelBean> labelBeanResult;
    private TextView tv_ok;
    private DraggedGridView gv_top;
    private GridView gv_bottoml;
    private List<LabelBean> list;
    private ArrayList<LabelBean> topLabels;
    private ArrayList<LabelBean> bottomLabels;
    private boolean flag;
    private GridViewAdapter topAdapter;
    private CustomerAdapter bottomAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.activity_addlabel, this.<ViewGroup>findViewById(R.id.ll_root)));
        initView();
        initData();
        initEvent();
        initListener();
    }

    private void initView() {
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        gv_top = (DraggedGridView) findViewById(R.id.gv_top);
        gv_bottoml = (GridView) findViewById(R.id.gv_bottoml);

        gv_top.setStatus(this);
    }

    private void initData() {
        list = labelBeanResult.getList();
        topLabels = new ArrayList<>();
        bottomLabels = new ArrayList<>();
        for (LabelBean labelBean : list) {
            if (labelBean.isSelect()) {
                topLabels.add(labelBean);
            } else {
                bottomLabels.add(labelBean);
            }
        }

        topAdapter = new GridViewAdapter();
        gv_top.setAdapter(topAdapter);

        bottomAdapter = new CustomerAdapter();
        gv_bottoml.setAdapter(bottomAdapter);
    }

    private void initEvent() {
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < topLabels.size(); i++) {
                    if (topLabels.get(i).isTop()) {
                        topLabels.get(i).setSelect(true);
                        topLabels.get(i).setNewsId(i);
                    }

                    for (LabelBean bottomLabel : bottomLabels) {
                        bottomLabel.setSelect(false);
                        bottomLabel.setNewsId(0);
                    }
                    labelBeanResult.refresh(list);
                    finish();
                }
            }
        });
    }

    @Override
    public void initListener() {
        super.initListener();
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < topLabels.size(); i++) {
                    if (topLabels.get(i).isSelect()) {
                        topLabels.get(i).setNewsId(i);
                    } else {
                        topLabels.get(i).setTop(false);
                    }
                }
                for (LabelBean bottomLabel : bottomLabels) {
                    if (bottomLabel.isSelect()) {
                        bottomLabel.setNewsId(topLabels.size());
                        bottomLabel.setTop(true);
                    } else {
                        bottomLabel.setTop(false);
                    }
                }

                labelBeanResult.refresh(list);
                finish();
            }
        });
    }

    @Override
    public void getStatus(boolean flag) {
        this.flag = flag;
        if (flag) {
            tv_ok.setVisibility(View.VISIBLE);
        }
        topAdapter.notifyDataSetChanged();
        bottomAdapter.notifyDataSetChanged();
    }

    public class GridViewAdapter extends BaseAdapter {
        private int hideProsition = AdapterView.INVALID_POSITION;
        private TextView tvLabelName;
        private ImageView ivIcon;

        @Override
        public int getCount() {
            return topLabels.size();
        }

        @Override
        public LabelBean getItem(int position) {
            return topLabels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(AddLabelActivity.this, R.layout.item_label, null);
            } else {
                view = convertView;
            }
            initItemView(view);
            if (hideProsition == position) {
                view.setVisibility(View.INVISIBLE);
            } else {
                view.setVisibility(View.VISIBLE);
            }
            ivIcon.setImageResource(R.mipmap.plus);
            if (flag) {
                ivIcon.setVisibility(View.VISIBLE);
            } else {
                ivIcon.setVisibility(View.INVISIBLE);
            }
            tvLabelName.setText(getItem(position).getLabelName());
            ivIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getItem(position).setTop(false);
                    bottomLabels.add(getItem(position));
                    topLabels.remove(position);
                    topAdapter.notifyDataSetChanged();
                    bottomAdapter.notifyDataSetChanged();
                }
            });
            return view;
        }

        public void hideView(int pos) {
            hideProsition = pos;
            notifyDataSetInvalidated();
        }

        public void showHideView() {
            hideProsition = AdapterView.INVALID_POSITION;
            notifyDataSetChanged();
        }

        public void swapView(int draggedPos, int destPos) {
            if (draggedPos < destPos) {
                topLabels.add(destPos + 1, getItem(draggedPos));
                topLabels.remove(draggedPos);
            }
            if (draggedPos > destPos) {
                topLabels.add(destPos, getItem(draggedPos));
                topLabels.remove(draggedPos + 1);
            }
            hideProsition = destPos;
            notifyDataSetChanged();
        }

        private void initItemView(View view) {
            tvLabelName = (TextView) view.findViewById(R.id.tv_label_name);
            ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        }
    }

    class CustomerAdapter extends BaseAdapter {

        private TextView tvLabelName;
        private ImageView ivIcon;

        @Override
        public int getCount() {
            return bottomLabels.size();
        }

        @Override
        public LabelBean getItem(int position) {
            return bottomLabels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(AddLabelActivity.this, R.layout.item_label, null);
            } else {
                view = convertView;
            }
            initItemView(view);
            ivIcon.setImageResource(R.mipmap.add2);
            if (flag) {
                ivIcon.setVisibility(View.VISIBLE);
            } else {
                ivIcon.setVisibility(View.INVISIBLE);
            }
            tvLabelName.setText(getItem(position).getLabelName());
            ivIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getItem(position).setTop(true);
                    topLabels.add(getItem(position));
                    bottomLabels.remove(position);
                    topAdapter.notifyDataSetChanged();
                    bottomAdapter.notifyDataSetChanged();
                }
            });
            return view;
        }

        private void initItemView(View view) {
            tvLabelName = (TextView) view.findViewById(R.id.tv_label_name);
            ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        }
    }
}
