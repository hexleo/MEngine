package com.hexleo.mengine.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hexleo.mengine.R;

/**
 * 顶部View
 * Created by hexleo on 2017/4/3.
 */

public class NavBarView extends FrameLayout implements View.OnClickListener{

    private View vRoot;
    private View vBack;
    private TextView tvTitle;
    private OnClickListener mListener;

    public NavBarView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public NavBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        vRoot = LayoutInflater.from(context).inflate(R.layout.widget_nav_bar, this, true);
        tvTitle = (TextView) vRoot.findViewById(R.id.title);
        vBack = vRoot.findViewById(R.id.back);
        vBack.setOnClickListener(this);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setOnBackClickListener(OnClickListener onClickListener) {
        mListener = onClickListener;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null && v.getId() == vBack.getId()) {
            mListener.onClick(v);
        }
    }
}
