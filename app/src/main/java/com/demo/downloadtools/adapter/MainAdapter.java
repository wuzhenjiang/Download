package com.demo.downloadtools.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.downloadtools.R;

import java.util.List;


/**
 * @author wzj
 * @time 2018/4/8 11:49
 * @des mainadapter
 */

public class MainAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public MainAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name,item);
    }
}
