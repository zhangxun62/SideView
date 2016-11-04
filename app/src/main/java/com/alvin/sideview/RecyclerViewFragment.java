package com.alvin.sideview;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AlphabetIndexer;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title ListViewFragment
 * @Description:
 * @Author: alvin
 * @Date: 2016/11/4.13:30
 * @E-mail: 49467306@qq.com
 */
public class RecyclerViewFragment extends Fragment {
    private static final String TAG = RecyclerViewFragment.class.getSimpleName();
    private View mView;
    private List<User> mDatas;
    private RecyclerView mRecyclerView;
    /**
     * 用于进行字母表分组
     */
    private AlphabetIndexer mIndexer;

    /**
     * 定义字母表的排序规则
     */
    private String alphabet = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private TextView titleTextView;

    /**
     * 弹出式分组的布局
     */
    private RelativeLayout sectionToastLayout;
    /**
     * 弹出式分组上的文字
     */
    private TextView sectionToastText;
    /**
     * 右侧可滑动字母表
     */
    private Button alphabetButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mDatas = getArguments().getParcelableArrayList("Datas");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mView)
            mView = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new TitleItemDecoration(getContext(), mDatas));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(getContext(), mDatas));
        return mView;
    }

    public static Fragment newInstance(List<User> list) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Datas", (ArrayList<? extends Parcelable>) list);
        fragment.setArguments(bundle);
        return fragment;
    }


}
