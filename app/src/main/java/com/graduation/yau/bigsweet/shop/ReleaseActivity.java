package com.graduation.yau.bigsweet.shop;

import android.os.Bundle;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.base.BaseActivity;

/**
 * Created by YAULEISIM on 2019/5/4.
 */

public class ReleaseActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_release);
        setTitleName(R.string.activity_release_title);
    }
}
