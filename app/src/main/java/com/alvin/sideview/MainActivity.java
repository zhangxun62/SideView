package com.alvin.sideview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 本文参考通过郭霖 博客,进行变体而得来
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<User> mDatas;
    private Button mListViewButton, mRecyclerViewButton;
    private Fragment mListViewFragment, mRecyclerViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListViewButton = (Button) findViewById(R.id.btn_listView);
        mListViewButton.setOnClickListener(this);
        mRecyclerViewButton = (Button) findViewById(R.id.btn_recyclerView);
        mRecyclerViewButton.setOnClickListener(this);
        if (null == mListViewFragment)
            mListViewFragment = ListViewFragment.newInstance(getDatas());
        if (null == mRecyclerViewFragment)
            mRecyclerViewFragment = RecyclerViewFragment.newInstance(getDatas());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, mListViewFragment, "listview")
                .add(R.id.container, mRecyclerViewFragment, "recyclerview")
                .show(mListViewFragment).hide(mRecyclerViewFragment).commitAllowingStateLoss();


    }


    private List<User> getPinyinAndHeader(List<User> list) {
        for (User user : list) {
            user.setPinyin(ParsePinyin.getPinyin(user.getName()));
            user.setHeader(ParsePinyin.getHeader(user.getPinyin()));
        }
        Collections.sort(list, new PinyinComparator());
        return list;
    }

    public List<User> getDatas() {
        if (null == mDatas)
            mDatas = new ArrayList<>();
        else
            mDatas.clear();
        mDatas.add(new User("张三"));
        mDatas.add(new User("张三"));
        mDatas.add(new User("张三"));
        mDatas.add(new User("张三"));
        mDatas.add(new User("张三"));
        mDatas.add(new User("张三"));
        mDatas.add(new User("李四"));
        mDatas.add(new User("李四"));
        mDatas.add(new User("李四"));
        mDatas.add(new User("李四"));
        mDatas.add(new User("李四"));
        mDatas.add(new User("李四"));
        mDatas.add(new User("李四"));
        mDatas.add(new User("网二"));
        mDatas.add(new User("网二"));
        mDatas.add(new User("网二"));
        mDatas.add(new User("网二"));
        mDatas.add(new User("网二"));
        mDatas.add(new User("网二"));
        mDatas.add(new User("网二"));
        mDatas.add(new User("的"));
        mDatas.add(new User("人"));
        mDatas.add(new User("去"));
        mDatas.add(new User("一"));
        mDatas.add(new User("哦"));
        mDatas.add(new User("平"));
        getPinyinAndHeader(mDatas);
        return mDatas;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_listView:
                getSupportFragmentManager().beginTransaction()
                        .show(mListViewFragment).hide(mRecyclerViewFragment).commitAllowingStateLoss();
                break;
            case R.id.btn_recyclerView:
                getSupportFragmentManager().beginTransaction()
                        .show(mRecyclerViewFragment).hide(mListViewFragment).commitAllowingStateLoss();
                break;
        }
    }

    public class PinyinComparator implements Comparator<User> {

        public int compare(User o1, User o2) {
            //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
            if (o2.getHeader().equals("#")) {
                return -1;
            } else if (o1.getHeader().equals("#")) {
                return 1;
            } else {
                return o1.getHeader().compareTo(o2.getHeader());
            }
        }
    }
}
