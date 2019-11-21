package com.lenovo.smarttraffic.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lenovo.smarttraffic.ui.activity.BaseActivity;
import com.lenovo.smarttraffic.util.CommonUtil;
import com.lenovo.smarttraffic.widget.ContentPage;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */
public abstract class BaseFragment extends SupportFragment implements View.OnClickListener {
    public ContentPage contentPage;
    public ProgressDialog pdLoading;
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*
         * 初始化pdLoading
         */
        pdLoading = new ProgressDialog(getActivity());
        pdLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdLoading.setMessage("请稍后");
        pdLoading.setCanceledOnTouchOutside(false);
        pdLoading.setCancelable(true);

        if (contentPage == null) {
            contentPage = new ContentPage(getActivity()) {
                @Override
                public Object loadData() {
                    return requestData();
                }

                @Override
                public View createSuccessView() {
                    return getSuccessView();
                }
            };
        } else {
            CommonUtil.removeSelfFromParent(contentPage);
        }
        return contentPage;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
    }

    /**
     * 初始化 Toolbar
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        ((BaseActivity) getActivity()).initToolBar(toolbar, homeAsUpEnabled, title);
    }
    /**
     * 刷新状态
     *
     */
    public void refreshPage(Object o) {
        contentPage.refreshPage(o);
    }

    /**
     * 返回据的fragment填充的具体View
     */
    protected abstract View getSuccessView();

    /**
     * 返回请求服务器的数据
     */
    protected abstract Object requestData();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

}