package com.scan.buxiaosheng.Helper;

/**
 * 作者:BertramTan
 * 创建日期 ： 2015-10-08
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 **/

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;


public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            synchronized (AppManager.class) {
                if (instance == null) {
                    instance = new AppManager();
                }
            }
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack == null) {
            return null;
        }
        Activity activity;
        try {
            activity = activityStack.lastElement();
        } catch (NoSuchElementException e) {
            return null;
        }
        return activity;
    }

    /**
     * 获取当前存在的activiti数量
     */
    public int currentActivitysSize() {
        if (activityStack == null) {
            return 0;
        }

        return activityStack.size();
    }

    /**
     * 获取当前Activity
     */
    public Activity getActivity(Activity activity) {
        while (activityStack != null) {
            int index = activityStack.indexOf(activity);
            if (index != -1) {
                return activityStack.get(index);
            }
        }
        return null;
    }

//    /**
//     * 结束所有Activity除了指定页
//     */
//    public boolean finishAllActivityNotLogin() {
//
//        boolean hasLead = false;//有启动页
//        if (activityStack == null) {
//            return hasLead;
//        }
//        int size = activityStack.size();
//        for (int i = size - 1; i > -1; i--) {
//            if (null != activityStack.get(i) && !(activityStack.get(i) instanceof LoginActivity)) {
//                finishActivity(activityStack.get(i));
//            } else {
//                if (!(activityStack.get(i) instanceof LoginActivity)) {
//                    activityStack.remove(i);
//                } else {
//                    hasLead = true;
//                }
//            }
//        }
//        return hasLead;
//    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if (activityStack == null) {
            return;
        }
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activityStack == null) {
            return;
        }
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }


    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack == null) {
            return;
        }
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

//    /**
//     * 结束所有页面，保留首页
//     */
//    public void finishActivityToMain() {
//        int size = activityStack.size();
//        for (int i = size - 1; i > -1; i--) {
//            if (null != activityStack.get(i) && !(activityStack.get(i) instanceof HomeActivity)) {
//                finishActivity(activityStack.get(i));
//            } else {
//                return;
//            }
//        }
//
//    }

    /**
     * 结束所有页面
     */
    public void finishAllActivity() {
        if (activityStack == null) {
            return;
        }
        int size = activityStack.size();
        for (int i = size - 1; i > -1; i--) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
                activityStack.remove(i);
            } else {

                activityStack.remove(i);
            }
        }
        if (activityStack != null) {
            activityStack.clear();
        }
    }


    /**
     * 获取应用图标
     */
    public Bitmap getAppIcon(Context context) {
        BitmapDrawable bitmapDrawable;
        Bitmap appIcon;
        bitmapDrawable = (BitmapDrawable) context
                .getApplicationInfo()
                .loadIcon(context.getPackageManager());
        appIcon = bitmapDrawable.getBitmap();
        return appIcon;
    }

    /**
     * 判断是否在当前界面
     */
    public boolean isAppForground(Context mContext) {

        ActivityManager am = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(mContext.getPackageName())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 退出应用程序
     */
    @SuppressWarnings("deprecation")
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
        }
    }


    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 通过进程id获取进程名字
     */
    public String getProcessName(Context context, int pid) {

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 判断指定进程id的进程是否运行中
     */
    public boolean isPidRunning(Context context, int pid) {

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return true;
            }
        }
        return false;
    }

    /**
     * 判断服务名称是否正在运行
     */
    public boolean isServiceWorked(Context context, String serviceName) {
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(Integer.MAX_VALUE);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }
}