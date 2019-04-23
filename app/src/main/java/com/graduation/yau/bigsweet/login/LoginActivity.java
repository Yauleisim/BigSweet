package com.graduation.yau.bigsweet.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.graduation.yau.bigsweet.MainActivity;
import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.model.User;
import com.graduation.yau.bigsweet.util.StartActivityUtil;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.util.TextUtil;
import com.graduation.yau.bigsweet.util.ToastUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by YAULEISIM on 2019/3/31.
 */

public class LoginActivity extends BaseActivity {

    private EditText mNumberEditText, mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_login);
        setTitleName(R.string.activity_login_title);
    }

    @Override
    protected void initView() {
        super.initView();
        mNumberEditText = findViewById(R.id.number_login_editText);
        mPasswordEditText = findViewById(R.id.password_login_editText);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        findViewById(R.id.register_login_textView).setOnClickListener(this);
        findViewById(R.id.forget_login_textView).setOnClickListener(this);
        findViewById(R.id.sign_in_login_button).setOnClickListener(this);
        findViewById(R.id.code_login_textView).setOnClickListener(this);
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
            case R.id.sign_in_login_button:
                doLogin();
                break;
            case R.id.code_login_textView:
                StartActivityUtil.go(LoginActivity.this, LoginWithPhoneActivity.class);
                finish();
                break;
            default:
                break;
        }
    }

    private void doLogin() {
        String number = mNumberEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        if (TextUtil.isEmpty(number)) {
            ToastUtil.show(this, R.string.activity_login_no_number, Toast.LENGTH_SHORT, false);
            return;
        }
        if (TextUtil.isEmpty(password)) {
            ToastUtil.show(this, R.string.activity_login_no_password, Toast.LENGTH_SHORT, false);
            return;
        }
        BmobUser.loginByAccount(number, password, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    // 登录成功
                    ToastUtil.show(LoginActivity.this, R.string.activity_login_success, Toast.LENGTH_SHORT, true);
                    StartActivityUtil.go(LoginActivity.this, MainActivity.class);
                    finish();
                } else {
                    // 登录失败
                    ToastUtil.show(LoginActivity.this, R.string.activity_login_fail, Toast.LENGTH_SHORT, false);
                    e.printStackTrace();
                }
            }
        });
    }
}
