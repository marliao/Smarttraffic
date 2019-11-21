package com.lenovo.smarttraffic.ui.fragment;

import android.content.Intent;
import android.os.SystemClock;
import android.telephony.PhoneStateListener;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.NewListBean;
import com.lenovo.smarttraffic.bean.NewsInfo;
import com.lenovo.smarttraffic.ui.customerActivity.NewDetailActivity;
import com.lenovo.smarttraffic.util.FileUtil;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import api.Update;

public class NewsFragment extends BaseFragment {
    private ListView lv_list;
    private List<NewListBean> newListBeans;
    private SimpleDateFormat simpleDateFormat;
    private CustomerAdapter customerAdapter;

    @Override

    protected View getSuccessView() {
        View inflate = View.inflate(getActivity(), R.layout.fragment_news, null);
        initView(inflate);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        initEvent();
        return inflate;
    }

    private void initView(View inflate) {
        lv_list = (ListView) inflate.findViewById(R.id.lv_list);
    }

    private void initEvent() {
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                App.setNewListBean(customerAdapter.getItem(position));
                Intent intent = new Intent(getActivity(), NewDetailActivity.class);
                intent.putExtra("title", customerAdapter.getItem(position).getTitle());
                startActivity(intent);
            }
        });
    }

    public void setNewListBeans(List<NewListBean> newListBeans) {
        this.newListBeans = newListBeans;
        refreshPage(newListBeans);
        if (newListBeans != null) {
            NewsInfo spot = (NewsInfo) FileUtil.getFileCalss("spot", NewsInfo.class);
            NewsInfo gonggao = (NewsInfo) FileUtil.getFileCalss("gonggao", NewsInfo.class);
            newListBeans.addAll(spot.getROWS_DETAIL());
            newListBeans.addAll(gonggao.getROWS_DETAIL());
            customerAdapter = new CustomerAdapter();
            lv_list.setAdapter(customerAdapter);
            for (int i = 0; i < newListBeans.size(); i++) {
                if (TextUtils.isEmpty(newListBeans.get(i).getCreatetime())) {
                    newListBeans.get(i).setCreatetime(simpleDateFormat.format(new Date()));
                }

                if (!TextUtils.isEmpty(newListBeans.get(i).getImg())) {
                    int finalI = i;
                    newListBeans.get(i).init(new Update() {
                        @Override
                        public void refresh() {
                            if (finalI == newListBeans.size() - 1) {
                                sort();
                                customerAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        } else {
            lv_list.setAdapter(null);
        }
    }

    private void sort() {
        Collections.sort(newListBeans, new Comparator<NewListBean>() {
            @Override
            public int compare(NewListBean o1, NewListBean o2) {
                Date parse = simpleDateFormat.parse(o1.getCreatetime(), new ParsePosition(0));
                Date parse1 = simpleDateFormat.parse(o2.getCreatetime(), new ParsePosition(0));
                return parse1.compareTo(parse);
            }
        });
    }

    @Override
    protected Object requestData() {
        SystemClock.sleep(1000);
        return newListBeans;
    }

    @Override
    public void onClick(View v) {

    }

    class CustomerAdapter extends BaseAdapter {
        private TextView tvTitle;
        private TextView tvDetail;
        private ImageView ivImage;
        private TextView tvTime;

        @Override
        public int getCount() {
            return newListBeans.size();
        }

        @Override
        public NewListBean getItem(int position) {
            return newListBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(getActivity(), R.layout.item_news, null);
            } else {
                view = convertView;
            }
            initItemView(view);
            tvTitle.setText(getItem(position).getTitle());
            tvDetail.setText(getItem(position).getContent());
            if (TextUtils.isEmpty(getItem(position).getImg())) {
                ivImage.setVisibility(View.GONE);
            } else {
                ivImage.setVisibility(View.VISIBLE);
                ivImage.setImageBitmap(getItem(position).getImage());
            }
            tvTime.setText(getItem(position).getCreatetime());
            return view;
        }

        private void initItemView(View view) {
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvDetail = (TextView) view.findViewById(R.id.tv_detail);
            ivImage = (ImageView) view.findViewById(R.id.iv_image);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
        }
    }

}
