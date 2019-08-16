package com.scan.buxiaosheng.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * UI utils
 * Created by Thong on 2017/11/20.
 */

public class UIUtils {
    public static Toast mToast = null;
    private static MaterialDialog dialog;

    /**
     * 弹出toast
     * @param msg message
     */
    public static void ToastMsg(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }else{
            mToast.setText(msg);
        }
        mToast.show();
    }

    /**
     * format date to String with yyyy-MM-dd HH:mm:ss
     *
     * @param date Date
     * @return String
     */
    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
    }

    /**
     * format double to String keep two point
     *
     * @param data double data
     * @return String
     */
    public static String doubleToString(double data) {
        DecimalFormat format = new DecimalFormat("#.00");
        return format.format(data);
    }

    /**
     * 设置view的间距
     * 0: top 1: right 2: bottom 3: left
     * @param margin the dip of margin
     */
    public static void setViewMargin(Context context, View view, int margin, int gravity){
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        switch (gravity){
            case 0:
                params.topMargin = DensityUtil.dip2px(context,margin);
                break;
            case 1:
                params.rightMargin = DensityUtil.dip2px(context,margin);
                break;
            case 2:
                params.bottomMargin = DensityUtil.dip2px(context,margin);
                break;
            case 3:
                params.leftMargin = DensityUtil.dip2px(context,margin);
                break;
        }
    }

    //显示加载框
    public static void showLoading(Context context, String str){
        if(null == dialog) {
            dialog = new MaterialDialog.Builder(context).progress(true, 100)
                    .content(str).build();
        }
        dialog.show();
    }

    //隐藏加载框
    public static void hideLoading(){
        if (null != dialog){
            dialog.hide();
            dialog = null;
        }
    }

    /**
     * 判断是否有虚拟按键
     */
    public static boolean hasNavigationBarFun(Activity activity) {
        Resources rs = activity.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        boolean hasNavBarFun = false;
        if (id > 0) {
            hasNavBarFun = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String)m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavBarFun = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavBarFun = true;
            }
        } catch (Exception e) {
            hasNavBarFun = false;
        }
        return hasNavBarFun;
    }
    /**
     * 隐藏虚拟按键，并且全屏
     */
    public static void hideNavigationBar(Activity activity){
            //隐藏虚拟按键，并且全屏
            if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
                View v = activity.getWindow().getDecorView();
                v.setSystemUiVisibility(View.GONE);
            } else if (Build.VERSION.SDK_INT >= 19) {
                //for new api versions.
                View decorView = activity.getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);
            }
    }

    public static void toResultActivity(Activity activity, Intent intent, int requestCode){
        activity.startActivityForResult(intent, requestCode);
    }

    public static void toActivity(Activity activity, Intent intent){
        activity.startActivity(intent);
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
}
