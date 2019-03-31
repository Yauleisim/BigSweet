package com.graduation.yau.bigsweet;

import android.os.Bundle;

import com.graduation.yau.bigsweet.base.BaseActivity;

/**
 * Created by YAULEISIM on 2019/3/31.
 */

public class ForgetPasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_forget_password);
        setTitleName(R.string.activity_forget_title);
    }
}
