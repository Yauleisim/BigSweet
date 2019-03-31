package com.graduation.yau.bigsweet.base;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.graduation.yau.bigsweet.R;

/**
 * Created by YAULEISIM on 2019/3/28.
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout mContentLayout;
    private ConstraintLayout mTitleLayout;
    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.title_common);
    }

    protected void loadContentLayout(int resId) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        mContentView = inflater.inflate(resId, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(layoutParams);

        mContentLayout = findViewById(R.id.content_title_common_constraintLayout);

        if (null != mContentLayout) {
            mContentLayout.addView(mContentView);
        }
        initView();
        initEvent();
    }

    protected void initView() {
        mTitleLayout = findViewById(R.id.title_title_common_constraintLayout);
    }

    protected void initEvent() {
        findViewById(R.id.back_title_common_imageView).setOnClickListener(this);
    }

    protected void hideTitle() {
        mTitleLayout.setVisibility(View.GONE);
    }

    protected void setTitleName(int titleName) {
        TextView titleNameTextView = findViewById(R.id.name_title_common_textView);
        titleNameTextView.setText(titleName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_title_common_imageView:
                finish();
                break;
            default:
                break;
        }
    }
}
