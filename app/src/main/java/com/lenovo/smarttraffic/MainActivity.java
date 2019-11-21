package com.lenovo.smarttraffic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.lenovo.smarttraffic.bean.LabelBean;
import com.lenovo.smarttraffic.bean.UBean;
import com.lenovo.smarttraffic.ui.activity.BaseActivity;
import com.lenovo.smarttraffic.ui.activity.Item1Activity;
import com.lenovo.smarttraffic.ui.activity.LoginActivity;
import com.lenovo.smarttraffic.ui.fragment.DesignFragment;
import com.lenovo.smarttraffic.ui.fragment.MainContentFragment;
import com.lenovo.smarttraffic.ui.lzz.CityCarParkingActivity;
import com.lenovo.smarttraffic.ui.lzz.SettingActivity;
import com.lenovo.smarttraffic.ui.lzz.WeathrActivity;
import com.lenovo.smarttraffic.usermanger.UserMangerActivity;
import com.lenovo.smarttraffic.util.FileUtil;
import com.lenovo.smarttraffic.util.OrmHelper;
import com.lenovo.smarttraffic.util.SpUtil;

import java.sql.SQLException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private MainContentFragment mMainContent;
    private DesignFragment mDesignFragment;
    private static final String POSITION = "position";
    private static final String SELECT_ITEM = "bottomItem";
    private static final int FRAGMENT_MAIN = 0;
    private static final int FRAGMENT_DESIGN = 1;
    private BottomNavigationView bottom_navigation;
    private Intent intent;
    private String title;
    //    private int position;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar(findViewById(R.id.toolbar), true, getString(R.string.title_main));
        initView();
        initData();
        setDao();
        if (savedInstanceState != null) {
            loadMultipleRootFragment(R.id.container, 0, mMainContent, mDesignFragment);   //使用fragmentation加载根组件
            // 恢复 recreate 前的位置
            showFragment(savedInstanceState.getInt(POSITION));
            bottom_navigation.setSelectedItemId(savedInstanceState.getInt(SELECT_ITEM));
        } else {
            showFragment(FRAGMENT_MAIN);
        }
    }

    private void showFragment(int index) {
//        position = index;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        TextView viewById = mToolbar.findViewById(R.id.tv_title);
        switch (index) {
            case FRAGMENT_MAIN:
                viewById.setText(R.string.title_main);
                if (mMainContent == null) {
                    mMainContent = MainContentFragment.getInstance();
                    ft.add(R.id.container, mMainContent, MainContentFragment.class.getName());
                } else {
                    ft.show(mMainContent);
                }
                break;
            case FRAGMENT_DESIGN:
                viewById.setText(R.string.creative_design);
                if (mDesignFragment == null) {
                    mDesignFragment = DesignFragment.getInstance();
                    ft.add(R.id.container, mDesignFragment, DesignFragment.class.getName());
                } else {
                    ft.show(mDesignFragment);
                }
                break;
        }
        ft.commit();

    }

    private void setDao() {
        try {
            OrmHelper instance = OrmHelper.getInstance();
            Dao<LabelBean, ?> labelBeanDao = instance.getDao(LabelBean.class);
            if (labelBeanDao.queryForAll().size() == 0) {
                labelBeanDao.create(new LabelBean(1, "推荐", true, true));
                labelBeanDao.create(new LabelBean(2, "热点", true, true));
                labelBeanDao.create(new LabelBean(3, "科技", true, true));
                labelBeanDao.create(new LabelBean(4, "汽车资讯 ", true, true));
                labelBeanDao.create(new LabelBean(5, "健康", false, false));
                labelBeanDao.create(new LabelBean(6, "财经", false, false));
                labelBeanDao.create(new LabelBean(7, "教育", false, false));
                labelBeanDao.create(new LabelBean(8, "旅游", false, false));
                labelBeanDao.create(new LabelBean(9, "军事", false, false));
                labelBeanDao.create(new LabelBean(10, "实时路况", false, false));
                labelBeanDao.create(new LabelBean(11, "文化", false, false));
                labelBeanDao.create(new LabelBean(12, "二手车", false, false));
                labelBeanDao.create(new LabelBean(13, "违章资讯", false, false));
                labelBeanDao.create(new LabelBean(14, "娱乐", false, false));
                labelBeanDao.create(new LabelBean(15, "体育", false, false));
                labelBeanDao.create(new LabelBean(16, "视频", false, false));
                labelBeanDao.create(new LabelBean(17, "游戏", false, false));
                labelBeanDao.create(new LabelBean(18, "电影", false, false));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void hideFragment(FragmentTransaction ft) {
        // 如果不为空，就先隐藏起来
        if (mMainContent != null) {
            ft.hide(mMainContent);
        }
        if (mDesignFragment != null) {
            ft.hide(mDesignFragment);
        }
    }

    private void initView() {
        UBean u = (UBean) FileUtil.getFileCalss("U", UBean.class);
        List<UBean.U> rows_detail = u.getROWS_DETAIL();
        mToolbar = findViewById(R.id.toolbar);
        mDrawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        CircleImageView imageView = navigationView.getHeaderView(0).findViewById(R.id.ivAvatar);
        TextView tv_2 = navigationView.getHeaderView(0).findViewById(R.id.textView);
        if (SpUtil.getBoolean(SpUtil.ISLOGIN, true)) {
            for (int i = 0; i < rows_detail.size(); i++) {
                if (rows_detail.get(i).getUsername().equals(SpUtil.getString(SpUtil.USERNAME, "user1"))) {
                    tv_2.setText("你好，" + rows_detail.get(i).getPname());
                }
            }

        }

        setSupportActionBar(mToolbar);
        imageView.setOnClickListener(this);
        /*设置选择item监听*/
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawer.openDrawer(GravityCompat.START);
        }
        return false;
    }

    private void initData() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        mDrawer.addDrawerListener(toggle);
        bottom_navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_main:
                    showFragment(FRAGMENT_MAIN);
                    break;
                case R.id.action_creative:
                    showFragment(FRAGMENT_DESIGN);
                    break;
            }
            return true;
        });

    }

    @Override
    public void onBackPressedSupport() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {    /*打开或关闭左边的菜单*/
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressedSupport();
            showExitDialog();
        }
    }

    /*是否退出项目*/
    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定退出吗");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", (dialogInterface, i) -> App.getInstance().exitApp());
        builder.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        title = item.getTitle().toString();
        switch (title) {
            case "个人":
                intent = new Intent(this, UserMangerActivity.class);
                intent.putExtra("title", "用户中心");
                startActivity(intent);
                break;
            case "交通资讯":
                intent = new Intent(this, Item1Activity.class);
                intent.putExtra("title", title);
                startActivity(intent);
                break;
            case "天气预报":
                intent = new Intent(this, WeathrActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
                break;
            case "设置":
                intent = new Intent(this, SettingActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
                break;
            case "离线地图":
                intent = new Intent(this, CityCarParkingActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
                break;
            case "关于":

                break;
        }
        mDrawer.closeDrawers();
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivAvatar) {    /*点击头像跳转登录界面*/
            if (!SpUtil.getBoolean(SpUtil.ISLOGIN, false)) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }
        mDrawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }
}
