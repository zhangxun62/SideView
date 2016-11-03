package com.alvin.sideview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

/**
 * @Title Adapter
 * @Description:
 * @Author: alvin
 * @Date: 2016/11/3.14:32
 * @E-mail: 49467306@qq.com
 */
public class Adapter extends ArrayAdapter<User> {
    private static final String TAG = Adapter.class.getSimpleName();
    /**
     * 字母表分组工具
     */
    private SectionIndexer mIndexer;
    private List<User> mList;

    public Adapter(Context context, List<User> objects) {
        super(context, 0, objects);
        mList = objects;
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listview, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        LinearLayout sortKeyLayout = (LinearLayout) convertView.findViewById(R.id.sort_key_layout);
        TextView sortKey = (TextView) convertView.findViewById(R.id.sort_key);
        name.setText(getItem(position).getName());


        int section = mIndexer.getSectionForPosition(position);
        Log.i(TAG,"" + section);
        if (position == mIndexer.getPositionForSection(section)) {
            sortKey.setText(getItem(position).getHeader());
            sortKeyLayout.setVisibility(View.VISIBLE);
        } else {
            sortKeyLayout.setVisibility(View.GONE);
        }
        return convertView;
    }

    public void setIndexer(SectionIndexer indexer) {
        mIndexer = indexer;
    }
}
