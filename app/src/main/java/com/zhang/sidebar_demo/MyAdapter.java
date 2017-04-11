package com.zhang.sidebar_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang . DATA: 2017/4/5 . Description : listview 的adapter 。
 */

public class MyAdapter extends BaseAdapter implements SectionIndexer {

    private Context mContext;

    private List<CityBean> mList = new ArrayList<>();

    public MyAdapter(Context mContext, List<CityBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            holder.textView = (TextView) convertView.findViewById(R.id.item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mList.get(position).getCity());
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        CityBean bean;
        String firstLetter;
        if (sectionIndex == '!') {
            return 0;
        } else {
            for (int i = 0; i < getCount(); i++) {
                bean = mList.get(i);
                // 取首字母
                firstLetter = PinyinUtils.getPinyinFirstLetter(bean.getCity());
                char firstChar = firstLetter.toUpperCase().charAt(0);
                if (firstChar == sectionIndex) {
                    return i;
                }
            }
        }
        bean = null;
        firstLetter = null;
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView textView;
    }
}
