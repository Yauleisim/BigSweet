package com.graduation.yau.bigsweet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.graduation.yau.bigsweet.base.BaseActivty;

/**
 * Created by YAULEISIM on 2019/3/24.
 */

public class PostActivity extends BaseActivty {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadLayout() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_post);
    }

    @Override
    protected void initEvent() {
        findViewById(R.id.back_post_imageView).setOnClickListener(this);
        findViewById(R.id.public_post_constraintLayout).setOnClickListener(this);
        findViewById(R.id.topic_post_constraintLayout).setOnClickListener(this);
        findViewById(R.id.location_post_constraintLayout).setOnClickListener(this);
        findViewById(R.id.send_post_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
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
