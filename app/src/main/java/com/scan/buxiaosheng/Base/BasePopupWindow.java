package com.scan.buxiaosheng.Base;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.scan.buxiaosheng.Utils.UIUtils;


/**
 * 抽象PopupWindow生成类
 * Created by BertramTan on 2018/4/12
 */
public abstract class BasePopupWindow extends PopupWindow {
    protected Context mContext;
    protected View mView;

    public BasePopupWindow(Context context) {
        mContext = context;
        mView = LayoutInflater.from(mContext).inflate(getLayoutId(),null,false);
        setContentView(mView);
        init();
    }

    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initData();
    private void init(){
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int width = dm.widthPixels * 3 / 4;
        setWidth(width);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        initView();
    }

    protected <T extends View> T getView(int ResId){
        T t = mView.findViewById(ResId);
        return t;
    }

    @Override
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        super.setOnDismissListener(onDismissListener);
    }

    /**
     * 显示在视图下方
     * @param mContainerView  rootView
     */
    public void showDown(View mContainerView){
        showAsDropDown(mContainerView);
    }

    /**
     * 显示位置
     * @param mContainerView rootView
     * @param gravity Gravity.LEFT ~~
     */
    public void showLocation(View mContainerView, int gravity){
        showAtLocation(mContainerView, gravity,0,0);
    }

    protected void showToast(String msg){
        UIUtils.ToastMsg(mContext,msg);
    }

    protected void showCenterToast(String msg){
        UIUtils.ToastMsg(mContext,msg);
    }
}
