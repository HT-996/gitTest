package com.scan.buxiaosheng.View.activity.login;

import com.scan.buxiaosheng.Base.BaseActivity;
import com.scan.buxiaosheng.R;
import com.scan.buxiaosheng.View.fragment.TestFragment;
import com.scan.buxiaosheng.databinding.ActivityLoginBinding;

/**
 * Created by Bertram on 2019/8/16
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewData() {
        mBinding.setText("点击");
        mBinding.tvText.setOnClickListener(v ->{
                mBinding.setText("已点击");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, new TestFragment())
                        .commit();
        });
    }
}
