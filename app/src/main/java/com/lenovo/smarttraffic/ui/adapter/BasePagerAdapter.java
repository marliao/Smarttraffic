package com.lenovo.smarttraffic.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.ui.fragment.FragmentFactory;
import com.lenovo.smarttraffic.ui.fragment.NewsFragment;
import com.lenovo.smarttraffic.util.CommonUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BasePagerAdapter extends FragmentStatePagerAdapter {

    private List<NewsFragment> fragmentList;
    private List<String> titleList;
    private String[] tabArr;


    public BasePagerAdapter(FragmentManager fm) {
        super(fm);
        this.tabArr = CommonUtil.getStringArray(R.array.tab_names);
    }

    public BasePagerAdapter(FragmentManager fm, List<NewsFragment> fragmentList, String[] titles) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = new ArrayList<>(Arrays.asList(titles));
    }

    public BasePagerAdapter(FragmentManager fm, List<NewsFragment> fragmentList, List<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    public void recreateItems(List<NewsFragment> fragmentList, List<String> titleList) {
        this.fragmentList = fragmentList;
        this.titleList = titleList;
        notifyDataSetChanged();
    }
}
