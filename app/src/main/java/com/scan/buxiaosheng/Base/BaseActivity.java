package com.scan.buxiaosheng.Base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.scan.buxiaosheng.Helper.AppManager;
import com.scan.buxiaosheng.Utils.UIUtils;

import org.simple.eventbus.EventBus;

/**
 * base Activity
 * Created by Thong on 2017/11/20.
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {
    protected Context mContext;
    protected View mRootView = null;
    protected MaterialDialog mDialog;
    private InputMethodManager imm;
    protected T mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialog = new MaterialDialog.Builder(this).cancelable(false).progress(true, 100).build();
        mContext = this;
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        EventBus.getDefault().register(this);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        mBinding = DataBindingUtil.setContentView(this,setLayoutId());
//        setContentView(setLayoutId());
        mRootView = getWindow().getDecorView().getRootView();
        AppManager.getAppManager().addActivity(this);
        initViewData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //处理点击页面缩回软键盘
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 判断是否隐藏软键盘
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                v.clearFocus();
                return true;
            }
        }
        return false;
    }

    /**
     * 绑定xml
     *
     * @return xml布局ID
     */
    protected abstract int setLayoutId();

    /**
     * 用户操作
     */
    protected abstract void initViewData();

    /**
     * 跳转页面
     */
    protected void toActivity(Intent intent) {
        startActivity(intent);
    }

    /**
     * 标记结果跳转页面
     */
    protected void toResultActivity(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    /**
     * 关闭当前Activity
     */
    protected void killMySelf() {
        AppManager.getAppManager().finishActivity(this);
    }

    //显示toast
    protected void showToast(String msg) {
        runOnUiThread(() -> UIUtils.ToastMsg(this, msg));
    }

    //显示居中toast
    protected void showCenterToast(String msg) {
        runOnUiThread(() -> {
            Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });
    }

    //显示等待窗  包含：标题、内容
    public void showLoading(String title, String msg) {
        runOnUiThread(() -> {
                    mDialog.setTitle(title);
                    mDialog.setContent(msg);
                    if (mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                    mDialog.show();
                }
        );
    }

    //显示等待窗  包含：内容
    public void showLoading(String msg) {
        showLoading("提示", msg);
    }

    //显示默认等待窗
    public void showLoading() {
        showLoading("提示", "请稍等...");
    }

    //隐藏等待窗
    public void hideLoding() {
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    //设置activity背景透明
    public void setWindowAlpha(float alpha) {
        if (alpha != 1) {
            mRootView.setBackgroundColor(Color.parseColor("#4D000000"));
        } else {
            mRootView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    /**
     * check the editText is empty
     *
     * @param view editText
     * @return boolean
     */
    public boolean checkEmpty(TextView view) {
        return TextUtils.isEmpty(view.getText().toString());
    }

    /**
     * check the object is null
     *
     * @param object obj
     * @return boolean
     */
    public boolean checkNull(Object object) {
        return null == object;
    }


}
