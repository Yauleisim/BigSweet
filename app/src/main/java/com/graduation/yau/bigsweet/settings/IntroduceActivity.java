package com.graduation.yau.bigsweet.settings;

import android.os.Bundle;
import android.widget.ImageView;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.util.TextUtil;
import com.winfo.photoselector.entity.Image;

/**
 * Created by YAULEISIM on 2019/5/3.
 */

public class IntroduceActivity extends BaseActivity {

    private String title;
    private ImageView mBgImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getIntent().getStringExtra("title");
        loadContentLayout(R.layout.activity_introduce);
        setTitleName(title);
    }

    @Override
    protected void initView() {
        super.initView();
        mBgImageView = findViewById(R.id.bg_introduce_imageView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        if (!TextUtil.isEmpty(title) && title.equals("功能介绍")) {
            mBgImageView.setImageResource(R.drawable.ic_about_introduce);
        } else if (!TextUtil.isEmpty(title) && title.equals("使用手册")) {
            mBgImageView.setImageResource(R.drawable.ic_about_use);
        }
    }
}
