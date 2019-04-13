package com.graduation.yau.bigsweet.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.yau.bigsweet.CountDownUtil;
import com.graduation.yau.bigsweet.MainActivity;
import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.User;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.util.StartActivityUtil;
import com.graduation.yau.bigsweet.util.TextUtil;
import com.graduation.yau.bigsweet.util.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by YAULEISIM on 2019/4/13.
 */

public class LoginWithPhoneActivity extends BaseActivity {

    private TextView mGetCodeTextView;
    private EditText mPhoneEditText, mCodeEditText;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_register_with_phone);
        setTitleName(R.string.activity_login_with_phone_title);
    }

    @Override
    protected void initView() {
        super.initView();
        mGetCodeTextView = findViewById(R.id.get_code_register_with_phone_textView);
        mPhoneEditText = findViewById(R.id.number_register_with_phone_editText);
        mCodeEditText = findViewById(R.id.code_register_with_phone_editText);
        mLoginButton = findViewById(R.id.sign_up_register_with_phone_button);
        mLoginButton.setText(R.string.activity_login_with_phone_sign_in);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mGetCodeTextView.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.get_code_register_with_phone_textView:
                requestCode();
                break;
            case R.id.sign_up_register_with_phone_button:
                doLogin();
                break;
            default:
                break;
        }
    }

    private void requestCode() {
        final String phone = mPhoneEditText.getText().toString();
        if (TextUtil.isEmpty(phone)) {
            ToastUtil.show(LoginWithPhoneActivity.this, R.string.activity_register_with_phone_no_phone, Toast.LENGTH_SHORT, false);
            return;
        }
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("mobilePhoneNumber", phone);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (object == null || object.size() == 0) {
                    ToastUtil.show(LoginWithPhoneActivity.this, R.string.activity_login_with_phone_no_exist_phone, Toast.LENGTH_SHORT, false);
                } else if (e == null) {
                    BmobSMS.requestSMSCode(phone, "一点甜", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer smsId, BmobException e) {
                            if (e == null) {
                                ToastUtil.show(LoginWithPhoneActivity.this, R.string.activity_register_with_phone_send_success, Toast.LENGTH_SHORT, true);
                                mGetCodeTextView.setBackgroundResource(R.drawable.bg_register_resend);
                                mGetCodeTextView.setClickable(false);
                                new CountDownUtil(60000, 1000, mGetCodeTextView).start();
                            } else {
                                ToastUtil.show(LoginWithPhoneActivity.this, R.string.activity_register_with_phone_send_fail, Toast.LENGTH_SHORT, false);
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void doLogin() {
        String phone = mPhoneEditText.getText().toString();
        if (TextUtil.isEmpty(phone)) {
            ToastUtil.show(LoginWithPhoneActivity.this, R.string.activity_register_with_phone_no_phone, Toast.LENGTH_SHORT, false);
            return;
        }
        String code = mCodeEditText.getText().toString();
        if (TextUtil.isEmpty(code)) {
            ToastUtil.show(LoginWithPhoneActivity.this, R.string.activity_register_with_phone_no_code, Toast.LENGTH_SHORT, false);
            return;
        }

        BmobUser.signOrLoginByMobilePhone(phone, code, new LogInListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    ToastUtil.show(LoginWithPhoneActivity.this, R.string.activity_login_with_phone_success, Toast.LENGTH_SHORT, true);
                    StartActivityUtil.goWithFlag(LoginWithPhoneActivity.this, MainActivity.class,
                            Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();
                } else {
                    ToastUtil.show(LoginWithPhoneActivity.this, R.string.activity_login_with_phone_fail, Toast.LENGTH_SHORT, false);
                    e.printStackTrace();
                }
            }
        });
    }
}
