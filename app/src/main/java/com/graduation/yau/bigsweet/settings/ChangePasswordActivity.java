package com.graduation.yau.bigsweet.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.login.LoginActivity;
import com.graduation.yau.bigsweet.util.StartActivityUtil;
import com.graduation.yau.bigsweet.util.TextUtil;
import com.graduation.yau.bigsweet.util.ToastUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by YAULEISIM on 2019/4/17.
 */

public class ChangePasswordActivity extends BaseActivity {

    private EditText mOldPasswordEditText, mNewPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_change_password);
        setTitleName(R.string.activity_change_password_title);
    }

    @Override
    protected void initView() {
        super.initView();
        mOldPasswordEditText = findViewById(R.id.old_password_change_password_editText);
        mNewPasswordEditText = findViewById(R.id.new_password_change_password_editText);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        findViewById(R.id.change_change_password_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.change_change_password_button:
                doChange();
                break;
            default:
                break;
        }
    }

    private void doChange() {
        String oldPassword = mOldPasswordEditText.getText().toString();
        if (TextUtil.isEmpty(oldPassword)) {
            ToastUtil.show(ChangePasswordActivity.this, R.string.activity_change_password_no_old_password, Toast.LENGTH_SHORT, false);
            return;
        }
        String newPassword = mNewPasswordEditText.getText().toString();
        if (TextUtil.isEmpty(newPassword)) {
            ToastUtil.show(ChangePasswordActivity.this, R.string.activity_change_password_no_new_password, Toast.LENGTH_SHORT, false);
            return;
        }
        if (newPassword.length() < 6) {
            ToastUtil.show(ChangePasswordActivity.this, R.string.activity_register_prompt, Toast.LENGTH_SHORT, false);
            return;
        }
        if (newPassword.equals(oldPassword)) {
            ToastUtil.show(ChangePasswordActivity.this, R.string.activity_change_password_error, Toast.LENGTH_SHORT, false);
            return;
        }
        BmobUser.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtil.show(ChangePasswordActivity.this, R.string.activity_change_password_success, Toast.LENGTH_SHORT, true);
                    StartActivityUtil.goWithFlag(ChangePasswordActivity.this, LoginActivity.class,
                            Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();
                } else {
                    ToastUtil.show(ChangePasswordActivity.this, R.string.activity_change_password_fail, Toast.LENGTH_SHORT, false);
                    e.printStackTrace();
                }
            }
        });
    }
}
