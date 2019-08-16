package com.scan.buxiaosheng.Base;

import java.io.Serializable;

/**
 * 实体基类，用于接收返回数据主体
 * Created by BertramTan on 2018/5/10
 */
public class BaseEntity<T> implements Serializable {
    private T data;
    private String msg;
    private int code;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
