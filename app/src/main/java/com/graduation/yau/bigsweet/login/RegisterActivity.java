package com.graduation.yau.bigsweet.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.User;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.util.StartActivityUtil;
import com.graduation.yau.bigsweet.util.TextUtil;
import com.graduation.yau.bigsweet.util.ToastUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by YAULEISIM on 2019/3/31.
 */

public class RegisterActivity extends BaseActivity {

    private EditText mNameEditText, mPassWordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_register);
        setTitleName(R.string.activity_register_title);
    }

    @Override
    protected void initView() {
        super.initView();
        mPassWordEditText = findViewById(R.id.password_register_editText);
        mNameEditText = findViewById(R.id.number_register_editText);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        findViewById(R.id.sign_up_register_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sign_up_register_button:
                doRegister();
                break;
        }
    }

    private void doRegister() {
        String phone = mNameEditText.getText().toString();
        String password = mPassWordEditText.getText().toString();
        if (TextUtil.isEmpty(phone)) {
            ToastUtil.show(this, R.string.activity_register_no_name, Toast.LENGTH_SHORT, false);
            return;
        }
        if (TextUtil.isEmpty(password)) {
            ToastUtil.show(this, R.string.activity_register_no_password, Toast.LENGTH_SHORT, false);
            return;
        }
        if (password.length() < 6) {
            ToastUtil.show(this, R.string.activity_register_prompt, Toast.LENGTH_SHORT, false);
            return;
        }
        final User user = new User();
        user.setUsername(phone);
        user.setPassword(password);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    ToastUtil.show(RegisterActivity.this, R.string.activity_register_success, Toast.LENGTH_SHORT, true);
                    StartActivityUtil.go(RegisterActivity.this, LoginActivity.class);
                    finish();
                } else {
                    e.printStackTrace();
                    ToastUtil.show(RegisterActivity.this, R.string.activity_register_fail, Toast.LENGTH_SHORT, false);
                }
            }
        });
    }
}
