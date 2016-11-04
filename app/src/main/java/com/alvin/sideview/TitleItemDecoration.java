package com.alvin.sideview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * @Title TitleItemDecoration
 * @Description:
 * @Author: alvin
 * @Date: 2016/11/4.14:48
 * @E-mail: 49467306@qq.com
 */
public class TitleItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = TitleItemDecoration.class.getSimpleName();
    private Context mContext;
    private float mTitleHeight;
    private List<User> mList;

    public TitleItemDecoration(Context context, List<User> list) {
        mContext = context;
        mTitleHeight = context.getResources().getDisplayMetrics().density * 30;
        mList = list;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position > -1) {
            if (position == 0) {
                outRect.set(0, (int) mTitleHeight, 0, 0);
            } else if (null != mList
                    && null != mList.get(position)
                    && !mList.get(position).getHeader().equals(mList.get(position - 1).getHeader())
                    ) {
                outRect.set(0, (int) mTitleHeight, 0, 0);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        }
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        View child = null;
        for (int i = 0; i < parent.getChildCount(); i++) {
            child = parent.getChildAt(i);
            if (null != child) {
                RecyclerView.LayoutParams prams = (RecyclerView.LayoutParams) child.getLayoutParams();
                int position = prams.getViewLayoutPosition();
                Paint mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setDither(true);
                mPaint.setTextSize(20);
                if (position > -1) {
                    if (position == 0) {
                        mPaint.setColor(mContext.getResources().getColor(R.color.colorAccent));
                        c.drawRect(left, child.getTop() - prams.topMargin - mTitleHeight, right, child.getTop() - prams.topMargin, mPaint);
                        Rect rect = new Rect();
                        mPaint.getTextBounds(mList.get(position).getHeader(), 0, mList.get(position).getHeader().length(), rect);
                        mPaint.setColor(Color.WHITE);
                        c.drawText(mList.get(position).getHeader(), child.getPaddingLeft(), child.getTop() - prams.topMargin - mTitleHeight / 2 + rect.height() / 2, mPaint);
                    } else if (null != mList
                            && null != mList.get(position)
                            && !mList.get(position).getHeader().equals(mList.get(position - 1).getHeader())) {
                        mPaint.setColor(mContext.getResources().getColor(R.color.colorAccent));
                        c.drawRect(left, child.getTop() - prams.topMargin - mTitleHeight, right, child.getTop() - prams.topMargin, mPaint);
                        Rect rect = new Rect();
                        mPaint.getTextBounds(mList.get(position).getHeader(), 0, mList.get(position).getHeader().length(), rect);
                        mPaint.setColor(Color.WHITE);
                        c.drawText(mList.get(position).getHeader(), child.getPaddingLeft(), child.getTop() - prams.topMargin - mTitleHeight / 2 + rect.height() / 2, mPaint);
                    }
                }
            }

        }

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
