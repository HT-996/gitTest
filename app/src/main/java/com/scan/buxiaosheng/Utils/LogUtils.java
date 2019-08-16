package com.scan.buxiaosheng.Utils;

import com.orhanobut.logger.Logger;

/**
 * 调试日志输出工具类
 * Created by Thong on 2017/11/20.
 */

public class LogUtils {
    private LogUtils() {
    }


    public static void d(String msg) {
        Logger.d(msg);
    }

    public static void i(String msg) {
        Logger.i(msg);
    }

    public static void t(String msg) {
        Logger.t(msg);
    }

    public static void e(String msg) {
        Logger.d(msg);
    }

    public static void json(String msg) {
        Logger.json(msg);
    }
}
