package com.alvin.sideview;

import android.os.Bundle;
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
public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private ArrayList<User> mDatas;
    private Adapter mAdapter;
    /**
     * 上次第一个可见元素，用于滚动时记录标识。
     */
    private int lastFirstVisibleItem = -1;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.id_listView);
        titleTextView = (TextView) findViewById(R.id.title);

        sectionToastLayout = (RelativeLayout) findViewById(R.id.section_toast_layout);
        sectionToastText = (TextView) findViewById(R.id.section_toast_text);
        alphabetButton = (Button) findViewById(R.id.alphabetButton);

        mDatas = new ArrayList<>();
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
        mAdapter = new Adapter(this, mDatas);
        mIndexer = new AlphabetIndexer(new MyCursor(mDatas), 0, alphabet);
        mAdapter.setIndexer(mIndexer);
        mListView.setAdapter(mAdapter);
        setAlpabetListener();
        initLisViewEvent();
    }

    /**
     * 设置字母表上的触摸事件，根据当前触摸的位置结合字母表的高度，计算出当前触摸在哪个字母上。
     * 当手指按在字母表上时，展示弹出式分组。手指离开字母表时，将弹出式分组隐藏。
     */
    private void setAlpabetListener() {
        alphabetButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float alphabetHeight = alphabetButton.getHeight();
                float y = event.getY();
                int sectionPosition = (int) ((y / alphabetHeight) / (1f / 27f));
                if (sectionPosition < 0) {
                    sectionPosition = 0;
                } else if (sectionPosition > 26) {
                    sectionPosition = 26;
                }
                String sectionLetter = String.valueOf(alphabet.charAt(sectionPosition));
                int position = mIndexer.getPositionForSection(sectionPosition);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        alphabetButton.setBackgroundResource(R.drawable.a_z_click);
                        sectionToastLayout.setVisibility(View.VISIBLE);
                        sectionToastText.setText(sectionLetter);
                        mListView.setSelection(position);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        sectionToastText.setText(sectionLetter);
                        mListView.setSelection(position);
                        break;
                    default:
                        alphabetButton.setBackgroundResource(R.drawable.a_z);
                        sectionToastLayout.setVisibility(View.GONE);
                }
                return true;
            }
        });
    }

    private void initLisViewEvent() {

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                SharedPreferencesUtils.init(getHoldingActivity()).saveDataString("汽车品牌", mDatas.get(position).getName());
//                getHoldingActivity().addFragment(ChooseCarTypeFragment.newInstance("获取汽车类型", mDatas.get(position).getId(), -1));
            }
        });
        if (mDatas.size() > 0) {
            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (view.getChildCount() > 0) {
                        int section = mIndexer.getSectionForPosition(firstVisibleItem);
                        int nextSecPosition = mIndexer.getPositionForSection(section + 1);
                        if (firstVisibleItem != lastFirstVisibleItem) {
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleTextView.getLayoutParams();
                            params.topMargin = 0;
                            titleTextView.setLayoutParams(params);

                            titleTextView.setText(String.valueOf(alphabet.charAt(section)));
                        }
                        if (nextSecPosition == firstVisibleItem + 1) {
                            View childView = view.getChildAt(0);
                            if (childView != null) {
                                int titleHeight = titleTextView.getHeight();
                                int bottom = childView.getBottom();
                                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleTextView
                                        .getLayoutParams();
                                if (bottom < titleHeight) {
                                    float pushedDistance = bottom - titleHeight;
                                    params.topMargin = (int) pushedDistance;
                                    titleTextView.setLayoutParams(params);
                                } else {
                                    if (params.topMargin != 0) {
                                        params.topMargin = 0;
                                        titleTextView.setLayoutParams(params);
                                    }
                                }
                            }
                        }
                        lastFirstVisibleItem = firstVisibleItem;
                    }
                }
            });
        }
    }

    private List<User> getPinyinAndHeader(List<User> list) {
        for (User user : list) {
            user.setPinyin(ParsePinyin.getPinyin(user.getName()));
            user.setHeader(ParsePinyin.getHeader(user.getPinyin()));
        }
        Collections.sort(list, new PinyinComparator());
        return list;
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
