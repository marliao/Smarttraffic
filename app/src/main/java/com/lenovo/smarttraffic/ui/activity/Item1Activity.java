package com.lenovo.smarttraffic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.ConstraintHorizontalLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.LabelBean;
import com.lenovo.smarttraffic.bean.NewListBean;
import com.lenovo.smarttraffic.bean.NewsInfo;
import com.lenovo.smarttraffic.ui.adapter.BasePagerAdapter;
import com.lenovo.smarttraffic.ui.customerActivity.AddLabelActivity;
import com.lenovo.smarttraffic.ui.fragment.NewsFragment;
import com.lenovo.smarttraffic.util.CommonUtil;
import com.lenovo.smarttraffic.util.OrmHelper;
import com.lenovo.smarttraffic.util.SpUtil;

import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import api.Result;
import butterknife.BindView;

import static com.android.volley.Request.Method.POST;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */

public class Item1Activity extends BaseActivity implements Result<LabelBean> {
    private static final String url = "http://192.168.1.104:8088/transportservice/action/GetNewsInfo.do";
    @BindView(R.id.tab_layout_list)
    TabLayout tabLayoutList;
    @BindView(R.id.header_layout)
    LinearLayout headerLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private Dao<LabelBean, ?> labelBeanDao;
    private List<LabelBean> labelBeans;
    private ArrayList<NewsFragment> newsFragments;
    private ArrayList<String> titles;
    private BasePagerAdapter basePagerAdapter;
    private ImageView iv_add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        InitView();
        InitData();
        initEvent();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_list_tab;
    }

    private void initView() {
        iv_add = (ImageView) findViewById(R.id.iv_add);
    }

    private void InitView() {
        initToolBar(findViewById(R.id.toolbar), true, getIntent().getStringExtra("title"));
        tabLayoutList.setupWithViewPager(viewPager);
        tabLayoutList.setTabMode(TabLayout.MODE_SCROLLABLE);
        headerLayout.setBackgroundColor(CommonUtil.getInstance().getColor());
    }

    private void InitData() {
        try {
            OrmHelper instance = OrmHelper.getInstance();
            labelBeanDao = instance.getDao(LabelBean.class);
            newsFragments = new ArrayList<>();
            titles = new ArrayList<>();
            labelBeans = labelBeanDao.queryForAll();
            sort(labelBeans);
            setFragment();
            basePagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), newsFragments, titles);
            viewPager.setAdapter(basePagerAdapter);
            viewPager.setOffscreenPageLimit(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setFragment() {
        newsFragments.clear();
        titles.clear();
        for (LabelBean labelBean : labelBeans) {
            if (labelBean.isSelect()) {
                newsFragments.add(new NewsFragment());
                titles.add(labelBean.getLabelName());
            }
        }
    }

    private void initEvent() {
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLabelActivity.labelBeanResult = Item1Activity.this;
                Intent intent = new Intent(Item1Activity.this, AddLabelActivity.class);
                intent.putExtra("title", "添加订阅");
                startActivity(intent);
            }
        });

        setData(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setData(position);
                SpUtil.putString(SpUtil.TYPE, titles.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setData(int position) {
        HashMap map = new HashMap();
        map.put("UserName", "user1");
        map.put("Category", 0);
        JSONObject jsonObject = new JSONObject(map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                NewsInfo newsInfo = new Gson().fromJson(jsonObject.toString(), NewsInfo.class);
                newsFragments.get(position).setNewListBeans(newsInfo.getROWS_DETAIL());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                newsFragments.get(position).setNewListBeans(null);
            }
        });
        App.getQueue().add(jsonObjectRequest);
    }


    @Override
    protected void onResume() {
        super.onResume();
        headerLayout.setBackgroundColor(CommonUtil.getInstance().getColor());
    }

    public void sort(List<LabelBean> labelBeans) {
        Collections.sort(labelBeans, new Comparator<LabelBean>() {
            @Override
            public int compare(LabelBean o1, LabelBean o2) {
                return o1.getNewsId() - o2.getNewsId();
            }
        });
    }


    @Override
    public List<LabelBean> getList() {
        return labelBeans;
    }

    @Override
    public void refresh(List<LabelBean> list) {
        try {
            for (LabelBean labelBean : list) {
                labelBeanDao.update(labelBean);
            }
            sort(list);
            setFragment();
            basePagerAdapter.recreateItems(newsFragments, titles);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(0);
                    tabLayoutList.setScrollPosition(0, 0, true);
                }
            }, 500);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() {

    }
}
