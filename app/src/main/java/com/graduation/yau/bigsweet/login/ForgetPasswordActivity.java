package com.graduation.yau.bigsweet.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.yau.bigsweet.util.CountDownUtil;
import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.User;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.util.TextUtil;
import com.graduation.yau.bigsweet.util.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by YAULEISIM on 2019/3/31.
 */

public class ForgetPasswordActivity extends BaseActivity {

    private EditText mPhoneEditText, mCodeEditText, mNewPasswordEditText;
    private TextView mGetCodeTextView;
    private Button mReSetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_forget_password);
        setTitleName(R.string.activity_forget_title);
    }

    @Override
    protected void initView() {
        super.initView();
        mPhoneEditText = findViewById(R.id.number_forget_editText);
        mCodeEditText = findViewById(R.id.code_forget_editText);
        mGetCodeTextView = findViewById(R.id.get_code_forget_textView);
        mReSetButton = findViewById(R.id.reset_forget_button);
        mNewPasswordEditText = findViewById(R.id.new_password_forget_editText);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mGetCodeTextView.setOnClickListener(this);
        mReSetButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.get_code_forget_textView:
                requestCode();
                break;
            case R.id.reset_forget_button:
                resetPassword();
                break;
            default:
                break;
        }
    }

    private void requestCode() {
        final String phone = mPhoneEditText.getText().toString();
        if (TextUtil.isEmpty(phone)) {
            ToastUtil.show(ForgetPasswordActivity.this, R.string.activity_register_with_phone_no_phone, Toast.LENGTH_SHORT, false);
            return;
        }
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("mobilePhoneNumber", phone);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (object == null || object.size() == 0) {
                    ToastUtil.show(ForgetPasswordActivity.this, R.string.activity_login_with_phone_no_exist_phone, Toast.LENGTH_SHORT, false);
                } else if (e == null) {
                    BmobSMS.requestSMSCode(phone, "一点甜", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer smsId, BmobException e) {
                            if (e == null) {
                                ToastUtil.show(ForgetPasswordActivity.this, R.string.activity_register_with_phone_send_success, Toast.LENGTH_SHORT, true);
                                mGetCodeTextView.setBackgroundResource(R.drawable.bg_register_resend);
                                mGetCodeTextView.setClickable(false);
                                new CountDownUtil(60000, 1000, mGetCodeTextView).start();
                            } else {
                                ToastUtil.show(ForgetPasswordActivity.this, R.string.activity_register_with_phone_send_fail, Toast.LENGTH_SHORT, false);
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

    private void resetPassword() {
        if (mPhoneEditText.getText() == null) {
            ToastUtil.show(ForgetPasswordActivity.this, R.string.activity_register_with_phone_no_phone, Toast.LENGTH_SHORT, false);
            return;
        }
        String code = mCodeEditText.getText().toString();
        if (TextUtil.isEmpty(code)) {
            ToastUtil.show(ForgetPasswordActivity.this, R.string.activity_register_with_phone_no_code, Toast.LENGTH_SHORT, false);
            return;
        }
        String newPassword = mNewPasswordEditText.getText().toString();
        if (TextUtil.isEmpty(newPassword)) {
            ToastUtil.show(ForgetPasswordActivity.this, R.string.activity_forget_no_new_password, Toast.LENGTH_SHORT, false);
            return;
        }

        BmobUser.resetPasswordBySMSCode(code, newPassword, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtil.show(ForgetPasswordActivity.this, R.string.activity_forget_reset_success, Toast.LENGTH_SHORT, true);
                    finish();
                } else {
                    ToastUtil.show(ForgetPasswordActivity.this, R.string.activity_forget_reset_fail, Toast.LENGTH_SHORT, false);
                    e.printStackTrace();
                }
            }
        });

    }
}
