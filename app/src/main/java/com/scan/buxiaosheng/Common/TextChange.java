package com.scan.buxiaosheng.Common;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * 为了方便添加文本改变事件监听写的
 * Created by Bertram on 2019/4/2
 */
public abstract class TextChange implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
