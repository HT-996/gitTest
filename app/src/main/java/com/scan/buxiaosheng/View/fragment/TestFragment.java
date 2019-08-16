package com.scan.buxiaosheng.View.fragment;

import com.scan.buxiaosheng.Base.BaseFragment;
import com.scan.buxiaosheng.R;
import com.scan.buxiaosheng.databinding.FragmentTestBinding;

/**
 * Created by Bertram on 2019/8/16
 */
public class TestFragment extends BaseFragment<FragmentTestBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initView() {
        mBinding.setFragmentTest("fragment点击测试");
        mBinding.fragmentText.setOnClickListener(v -> mBinding.setFragmentTest("已点击"));
    }
}
