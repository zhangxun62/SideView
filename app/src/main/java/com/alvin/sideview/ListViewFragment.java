package com.alvin.sideview;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
public class ListViewFragment extends Fragment {
    private static final String TAG = ListViewFragment.class.getSimpleName();
    private View mView;
    private ListView mListView;
    private List<User> mDatas;
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
            mView = inflater.inflate(R.layout.fragment_listview, container, false);

        mListView = (ListView) mView.findViewById(R.id.id_listView);
        titleTextView = (TextView) mView.findViewById(R.id.title);

        sectionToastLayout = (RelativeLayout) mView.findViewById(R.id.section_toast_layout);
        sectionToastText = (TextView) mView.findViewById(R.id.section_toast_text);
        alphabetButton = (Button) mView.findViewById(R.id.alphabetButton);

        mAdapter = new Adapter(getContext(), mDatas);
        mIndexer = new AlphabetIndexer(new MyCursor(mDatas), 0, alphabet);
        mAdapter.setIndexer(mIndexer);
        mListView.setAdapter(mAdapter);
        setAlpabetListener();
        initLisViewEvent();
        return mView;
    }

    public static Fragment newInstance(List<User> list) {
        ListViewFragment fragment = new ListViewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Datas", (ArrayList<? extends Parcelable>) list);
        fragment.setArguments(bundle);
        return fragment;
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
}
