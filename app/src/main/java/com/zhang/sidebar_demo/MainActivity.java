package com.zhang.sidebar_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    private SideBar sideBar;

    private RelativeLayout activity_main;

    private List<CityBean> mDatas;

    private MyAdapter mAdapter;

    private TextView mDialogText;

    private SizeUtils sizeUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sizeUtils = new SizeUtils(this);
        initData();
        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.mListView);
        sideBar = (SideBar) findViewById(R.id.sideBar);
        activity_main = (RelativeLayout) findViewById(R.id.activity_main);

        mAdapter = new MyAdapter(this, mDatas);
        mListView.setAdapter(mAdapter);

        sideBar.setSectionIndexer((SectionIndexer) mListView.getAdapter());

        sideBar.setOnSelecListener(new SideBar.onSelecListener() {
            @Override
            public void setSelection(int position) {
                mListView.setSelection(position);
            }
        });
    }

    private void initData() {
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
}
