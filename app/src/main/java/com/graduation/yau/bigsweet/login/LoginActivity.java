package com.graduation.yau.bigsweet.login;

import android.os.Bundle;
import android.view.View;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.util.StartActivityUtil;
import com.graduation.yau.bigsweet.base.BaseActivity;

/**
 * Created by YAULEISIM on 2019/3/31.
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_login);
        setTitleName(R.string.activity_login_title);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        findViewById(R.id.register_login_textView).setOnClickListener(this);
        findViewById(R.id.forget_login_textView).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.register_login_textView:
                StartActivityUtil.go(LoginActivity.this, RegisterActivity.class);
                break;
            case R.id.forget_login_textView:
                StartActivityUtil.go(LoginActivity.this, ForgetPasswordActivity.class);
                break;
            default:
                break;
        }
    }
}
