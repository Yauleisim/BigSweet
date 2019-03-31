package com.graduation.yau.bigsweet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
                Intent mIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(mIntent);
                break;
            case R.id.forget_login_textView:
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
