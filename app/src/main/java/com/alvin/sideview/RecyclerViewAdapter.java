package com.alvin.sideview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

/**
 * @Title RecyclerViewAdapter
 * @Description:
 * @Author: alvin
 * @Date: 2016/11/4.15:03
 * @E-mail: 49467306@qq.com
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();
    private Context mContext;
    private List<User> mList;
    private SectionIndexer mIndexer;

    public RecyclerViewAdapter(Context context, List<User> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.mTextView.setText(mList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (null != mList && mList.size() > 0)
            return mList.size();
        else
            return 0;
    }

    public void setIndexer(SectionIndexer indexer) {
        mIndexer = indexer;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
