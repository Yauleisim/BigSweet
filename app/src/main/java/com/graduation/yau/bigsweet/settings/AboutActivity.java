package com.graduation.yau.bigsweet.settings;

import android.os.Bundle;
import android.view.View;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.util.StartActivityUtil;

/**
 * Created by YAULEISIM on 2019/4/2.
 */

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_about);
        setTitleName(R.string.activity_about_title);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        findViewById(R.id.introduce_about_constraintLayout).setOnClickListener(this);
        findViewById(R.id.use_about_constraintLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.introduce_about_constraintLayout:
                StartActivityUtil.goWithTitle(AboutActivity.this, IntroduceActivity.class, "功能介绍");
                break;
            case R.id.use_about_constraintLayout:
                StartActivityUtil.goWithTitle(AboutActivity.this, IntroduceActivity.class, "使用手册");
                break;
            default:
                break;
        }
    }
}
