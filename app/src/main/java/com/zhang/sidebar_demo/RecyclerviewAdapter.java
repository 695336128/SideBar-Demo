package com.zhang.sidebar_demo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang . DATA: 2017/4/5 . Description : mRecyclerView 的adapter
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>
        implements SectionIndexer {

    private List<CityBean> mList = new ArrayList<>();

    public RecyclerviewAdapter(List<CityBean> mList){
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(mList.get(position).getCity());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        CityBean bean ;
        String firstLetter;
        if (sectionIndex == '!'){
            return 0;
        }else {
            for (int i = 0; i < mList.size();i++){
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

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_text);
        }
    }
}
