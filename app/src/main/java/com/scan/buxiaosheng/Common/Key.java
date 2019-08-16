package com.scan.buxiaosheng.Common;

import android.content.Context;

import com.google.gson.Gson;
import com.vondear.rxtool.RxAppTool;

import java.util.TreeMap;

/**
 * 请求校验生成类
 * Created by BertramTan on 2018/5/9
 */
public class Key {

    public static String config;

    public static String sign = "77938A22512F3A05F180A9142280786B";

    public static TreeMap<String, Object> getModel(Context context) {
        config = "{\"appName\":\"buxiaosheng_android\"," +
                "\"appVersion\":\"" + RxAppTool.getAppVersionName(context) + "\"}";
        TreeMap<String, Object> map = new Gson().fromJson(config, TreeMap.class);
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return map;
    }
}
