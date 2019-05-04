package com.graduation.yau.bigsweet.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.model.User;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.util.StartActivityUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;

/**
 * Created by YAULEISIM on 2019/4/1.
 */

public class AccountSecurityActivity extends BaseActivity {

    private TextView mPhoneTextView, mEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_account_security);
        setTitleName(R.string.activity_account_security_title);
    }

    @Override
    protected void initView() {
        super.initView();
        mPhoneTextView = findViewById(R.id.phone_account_security_textView);
        mEmailTextView = findViewById(R.id.email_account_security_textView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        findViewById(R.id.password_account_security_constraintLayout).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BmobUser.fetchUserInfo(new FetchUserInfoListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                User user = BmobUser.getCurrentUser(User.class);
                if (user.getMobilePhoneNumberVerified() == null || !user.getMobilePhoneNumberVerified()) {
                    // 没有绑定手机号
                    mPhoneTextView.setText(R.string.activity_account_security_no);
                    findViewById(R.id.phone_account_security_constraintLayout).setOnClickListener(AccountSecurityActivity.this);
                } else {
                    // 有绑定手机号
                    mPhoneTextView.setText(user.getMobilePhoneNumber());
                }

                if (user.getEmailVerified() == null || !user.getEmailVerified()) {
                    mEmailTextView.setText(R.string.activity_account_security_no);
                    findViewById(R.id.email_account_security_constraintLayout).setOnClickListener(AccountSecurityActivity.this);
                } else {
                    mEmailTextView.setText(user.getEmail());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.phone_account_security_constraintLayout:
                // 绑定手机
                StartActivityUtil.go(AccountSecurityActivity.this, BindPhoneActivity.class);
                break;
            case R.id.email_account_security_constraintLayout:
                // 绑定邮箱
                StartActivityUtil.go(AccountSecurityActivity.this, BindEmailActivity.class);
                break;
            case R.id.password_account_security_constraintLayout:
                // 修改密码
                StartActivityUtil.go(AccountSecurityActivity.this, ChangePasswordActivity.class);
                break;
            default:
                break;
        }
    }
}
