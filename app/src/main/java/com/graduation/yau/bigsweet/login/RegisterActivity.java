package com.graduation.yau.bigsweet.login;

import android.os.Bundle;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.base.BaseActivity;

/**
 * Created by YAULEISIM on 2019/3/31.
 */

public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_register);
        setTitleName(R.string.activity_register_title);
    }
}
