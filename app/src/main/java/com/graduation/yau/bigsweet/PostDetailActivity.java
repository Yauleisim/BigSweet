package com.graduation.yau.bigsweet;

import android.os.Bundle;

import com.graduation.yau.bigsweet.base.BaseActivity;

/**
 * Created by YAULEISIM on 2019/4/24.
 */

public class PostDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_post_detail);
        setTitleName(R.string.activity_post_detail_title);
    }
}
