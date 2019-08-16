package com.scan.buxiaosheng.HttpUtils;

import android.content.Context;


import com.scan.buxiaosheng.Common.Key;
import com.scan.buxiaosheng.Helper.UserHelper;
import com.scan.buxiaosheng.Utils.MD5Utils;
import com.scan.buxiaosheng.View.activity.login.LoginActivity;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 生成签名类
 * Created by BertramTan on 2018/5/9
 */
public class SignatureUtils {
    private Map<String, Object> keyMap;
    private static SignatureUtils signatureUtils = new SignatureUtils();

    public static SignatureUtils getInstance() {
        return signatureUtils;
    }

    public Map<String, Object> getSignature(Context context, HashMap<String, Object> map) {
        keyMap = null;
        keyMap = Key.getModel(context);
        if (!(context instanceof LoginActivity)) {
            keyMap.put("userId", UserHelper.getInstance().getUserId(context));
            keyMap.put("token", UserHelper.getInstance().getToken(context));
        }
        if (null != map) {
            Set<String> keySet = map.keySet();
            Iterator<String> keys = keySet.iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                keyMap.put(key, map.get(key));
            }
        }
        keyMap.put("sign", getKeyStr());
        return keyMap;
    }

    private String getKeyStr() {
        String keystr = "";
        Collection<Object> values = keyMap.values();
        Iterator<Object> value = values.iterator();
        while (value.hasNext()) {
            Object v = value.next();
            if (null != v) {
                if (!(v instanceof File)) {
                    keystr += v.toString();
                }
            }
        }
        keystr += Key.sign;
        keystr = MD5Utils.md5(keystr).toUpperCase();
        return keystr;
    }
}
