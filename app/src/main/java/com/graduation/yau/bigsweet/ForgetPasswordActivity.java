package com.graduation.yau.bigsweet;

import android.os.Bundle;

import com.graduation.yau.bigsweet.base.BaseActivty;

/**
 * Created by YAULEISIM on 2019/3/31.
 */

public class ForgetPasswordActivity extends BaseActivty {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_forget_password);
        setTitle(R.string.activity_forget_title);
    }
}
