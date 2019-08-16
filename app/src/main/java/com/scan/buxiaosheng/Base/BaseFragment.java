package com.scan.buxiaosheng.Base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.scan.buxiaosheng.Utils.UIUtils;

import org.simple.eventbus.EventBus;

/**
 * base Fragment
 * Created by Thong on 2017/11/24.
 */

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {
    protected BaseActivity mContext;
    protected boolean isVisible = false;//Fragment可视
    protected boolean isPrepared = false;//Fragment是否已加载视图
    protected boolean isCompelete = false;//Fragment是否已完成加载
    protected T mBinding;

    protected abstract int getLayoutId();

    protected abstract void initView();

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.mContext = (BaseActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    protected void toActivity(Intent intent) {
        startActivity(intent);
    }

    protected void toResultActivity(Intent intent, int requestcode) {
        startActivityForResult(intent, requestcode);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    protected void showToast(String msg) {
        mContext.runOnUiThread(() -> UIUtils.ToastMsg(getActivity(), msg));
    }

    protected void showCenterToast(String msg) {
        mContext.runOnUiThread(() -> {
            Toast toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });
    }

    protected void showLoading() {
        if (null != mContext.mDialog) {
            mContext.showLoading();
        }
    }

    protected void showLoading(String msg) {
        if (null != mContext.mDialog) {
            mContext.showLoading(msg);
        }
    }

    protected void showLoading(String title, String msg) {
        if (null != mContext.mDialog) {
            mContext.showLoading(title, msg);
        }
    }

    protected void hideLoding() {
        if (null != mContext.mDialog) {
            mContext.hideLoding();
        }
    }
}
