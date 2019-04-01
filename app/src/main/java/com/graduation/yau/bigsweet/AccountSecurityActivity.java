package com.graduation.yau.bigsweet;

import android.os.Bundle;

import com.graduation.yau.bigsweet.base.BaseActivity;

/**
 * Created by YAULEISIM on 2019/4/1.
 */

public class AccountSecurityActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_account_security);
        setTitleName(R.string.activity_account_security_title);
    }
}
