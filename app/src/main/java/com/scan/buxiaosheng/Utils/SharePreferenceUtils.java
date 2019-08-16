package com.scan.buxiaosheng.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * 本地存储工具类
 * Created by Thong on 2017/11/24.
 */

public class SharePreferenceUtils {

    private static SharePreferenceUtils mySelf = new SharePreferenceUtils();
    private static SharedPreferences sharedPreferences = null;
    private static SharedPreferences.Editor editor = null;

    /**
     * Instantiation the sharePreferences
     *
     * @param context context
     * @param sp_name name of sharepreference
     * @return sharepreference
     */
    public static SharePreferenceUtils getDefault(Context context, String sp_name) {
        sharedPreferences = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
        return mySelf;
    }

    /**
     * save the key-value to sharepreference
     * @param key key
     * @param value value
     * @return sharepreference
     */
    public SharedPreferences put(String key, Object value) {
        editor = sharedPreferences.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else {
            LogUtils.e("不支持当前类型");
            new Throwable("不支持当前类型").printStackTrace();
        }
        editor.apply();
        return sharedPreferences;
    }

    public long get(String key, long defalut){
        return sharedPreferences.getLong(key, defalut);
    }

    public boolean get(String key, boolean defalut){
        return sharedPreferences.getBoolean(key, defalut);
    }

    public float get(String key, float defalut){
        return sharedPreferences.getFloat(key, defalut);
    }

    public int get(String key, int defalut){
        if (TextUtils.isEmpty(String.valueOf(sharedPreferences.getInt(key, defalut)))){
            return 0;
        }
        return sharedPreferences.getInt(key, defalut);
    }

    public String get(String key, String defalut){
        return sharedPreferences.getString(key, defalut);
    }
}
