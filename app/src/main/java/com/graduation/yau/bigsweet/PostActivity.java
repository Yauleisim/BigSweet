package com.graduation.yau.bigsweet;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.graduation.yau.bigsweet.base.BaseActivity;

/**
 * Created by YAULEISIM on 2019/3/24.
 */

public class PostActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_post);
        hideTitle();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        findViewById(R.id.back_post_imageView).setOnClickListener(this);
        findViewById(R.id.public_post_constraintLayout).setOnClickListener(this);
        findViewById(R.id.topic_post_constraintLayout).setOnClickListener(this);
        findViewById(R.id.location_post_constraintLayout).setOnClickListener(this);
        findViewById(R.id.send_post_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.back_post_imageView:
                finish();
                break;
            case R.id.send_post_button:
                // 发贴
                break;
            case R.id.location_post_constraintLayout:
                break;
            case R.id.topic_post_constraintLayout:
                break;
            case R.id.public_post_constraintLayout:
                break;
            default:
                break;
        }
    }
}
