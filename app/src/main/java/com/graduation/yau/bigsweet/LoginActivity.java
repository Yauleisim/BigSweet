package com.graduation.yau.bigsweet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.graduation.yau.bigsweet.base.BaseActivty;

/**
 * Created by YAULEISIM on 2019/3/31.
 */

public class LoginActivity extends BaseActivty {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_login);
        setTitle(R.string.activity_login_title);
    }

    @Override
    protected void initEvent() {
        findViewById(R.id.register_login_textView).setOnClickListener(this);
        findViewById(R.id.forget_login_textView).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
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
