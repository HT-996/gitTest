package com.scan.buxiaosheng.HttpUtils;

/**
 * 请求KeyValue
 * Created by BertramTan on 2018/5/9
 */
public class RequestKeyValue {
    private Object key;
    private Object value;

    public RequestKeyValue(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
