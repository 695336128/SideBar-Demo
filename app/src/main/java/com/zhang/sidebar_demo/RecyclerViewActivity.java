package com.zhang.sidebar_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private SideBar sideBar;

    private RecyclerviewAdapter mAdapter;

    private TextView mDialogText;

    private List<CityBean> mDatas;

    private SizeUtils sizeUtils;

    /** 目标项是否在最后一个可见项之后*/
    private boolean mShouldScroll;
    /** 记录目标项位置*/
    private int mToPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        sizeUtils = new SizeUtils(this);
        initDatas();
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        sideBar = (SideBar) findViewById(R.id.sideBar);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerviewAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        sideBar.setSectionIndexer((SectionIndexer) mRecyclerView.getAdapter());
        sideBar.setOnSelecListener(new SideBar.onSelecListener() {
            @Override
            public void setSelection(int position) {
//                mRecyclerView.scrollToPosition(position);
                smoothMoveToPosition(mRecyclerView,position);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mShouldScroll){
                    mShouldScroll = false;
                    smoothMoveToPosition(mRecyclerView,mToPosition);
                }
            }
        });
    }

    private void initDatas() {
        String[] data = getResources().getStringArray(R.array.provinces);
        mDatas = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length - i - 1; j++) {
                if (PinyinUtils.getPinyinFirstLetter(data[j])
                        .compareTo(PinyinUtils.getPinyinFirstLetter(data[j + 1])) > 0) {
                    String tmp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = tmp;
                }
            }

        }
        for (int i = 0; i < data.length; i++) {
            CityBean cityBean = new CityBean(PinyinUtils.getPinyinFirstLetter(data[i]).toUpperCase(), data[i]);
            mDatas.add(cityBean);
        }
    }

    /**
     * 滑动到指定位置
     * @param mRecyclerView
     * @param position
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));

        if (position < firstItem) {
            // 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在第一个可见项之后，最后一个可见项之前
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        }else {
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，进入上一个控制语句
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }
}
