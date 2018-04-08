package com.demo.downloadtools.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.demo.downloadtools.R;
import com.demo.downloadtools.adapter.MainAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private Unbinder mUnbinder;
    private MainAdapter mMainAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private  List<String> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        initData();
        addListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    private void initData() {
        String[] stringArray = getResources().getStringArray(R.array.main_data);
        mData = Arrays.asList(stringArray);


        mMainAdapter = new MainAdapter(R.layout.item_main, mData);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mMainAdapter);
    }

    private void addListener() {
        mMainAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(position==0){
                    Intent intent=new Intent(MainActivity.this,DownloadApkActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
