package com.graduation.yau.bigsweet.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.User;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.util.TextUtil;
import com.graduation.yau.bigsweet.util.ToastUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by YAULEISIM on 2019/4/2.
 */

public class UserMessageActivity extends BaseActivity {

    private EditText mNameEditText, mSignatureEditText, mAddressEditText;
    private RadioButton mMaleRadioButton, mFemaleRadioButton;
    private String mName, mSignature, mAddress, mGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_user_message);
        setTitleName(R.string.activity_user_msg_title);
    }

    @Override
    protected void initView() {
        super.initView();
        mNameEditText = findViewById(R.id.name_user_message_editText);
        mSignatureEditText = findViewById(R.id.signature_user_message_editText);
        mAddressEditText = findViewById(R.id.address_user_message_editText);
        mMaleRadioButton = findViewById(R.id.male_user_message_radioButton);
        mFemaleRadioButton = findViewById(R.id.female_user_message_radioButton);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        User currentUser = BmobUser.getCurrentUser(User.class);
        mName = currentUser.getUsername();
        if (!TextUtil.isEmpty(mName)) {
            mNameEditText.setText(mName);
        }
        mAddress = currentUser.getAddress();
        if (!TextUtil.isEmpty(mAddress)) {
            mAddressEditText.setText(mAddress);
        }
        mSignature = currentUser.getSignature();
        if (!TextUtil.isEmpty(mSignature)) {
            mSignatureEditText.setText(mSignature);
        }
        mGender = currentUser.getGender();
        if (!TextUtil.isEmpty(mGender) && mGender.equals(getString(R.string.activity_user_msg_female))) {
            mFemaleRadioButton.setChecked(true);
            mMaleRadioButton.setChecked(false);
            mGender = getString(R.string.activity_user_msg_female);
        } else {
            mFemaleRadioButton.setChecked(false);
            mMaleRadioButton.setChecked(true);
            mGender = getString(R.string.activity_user_msg_male);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        doSave();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_title_common_imageView:
                doSave();
                break;
            default:
                break;
        }
    }

    private void doSave() {
        final User user = BmobUser.getCurrentUser(User.class);
        boolean flag = false;
        String name = mNameEditText.getText().toString();
        if (!TextUtil.equals(mName, name)) {
            user.setUsername(name);
            flag = true;
        } else {
            user.setUsername(null);
        }
        String address = mAddressEditText.getText().toString();
        if (!TextUtil.equals(address, mAddress)) {
            user.setAddress(address);
            flag = true;
        }
        String signature = mSignatureEditText.getText().toString();
        if (!TextUtil.equals(signature, mSignature)) {
            user.setSignature(signature);
            flag = true;
        }
        if (mMaleRadioButton.isChecked() && mGender.equals(getString(R.string.activity_user_msg_female))) {
            user.setGender(getString(R.string.activity_user_msg_male));
            flag = true;
        } else if (mFemaleRadioButton.isChecked() && mGender.equals(getString(R.string.activity_user_msg_male))) {
            user.setGender(getString(R.string.activity_user_msg_female));
            flag = true;
        }
        if (!flag) {
            finish();
            return;
        }
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtil.show(UserMessageActivity.this, R.string.activity_user_msg_success, Toast.LENGTH_SHORT, true);
                    finish();
                } else {
                    e.printStackTrace();
                    ToastUtil.show(UserMessageActivity.this, R.string.activity_user_msg_fail, Toast.LENGTH_SHORT, false);
                }
            }
        });
    }
}
