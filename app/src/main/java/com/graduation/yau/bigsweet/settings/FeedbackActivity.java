package com.graduation.yau.bigsweet.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.model.Feedback;
import com.graduation.yau.bigsweet.util.TextUtil;
import com.graduation.yau.bigsweet.util.ToastUtil;

import java.util.regex.Pattern;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by YAULEISIM on 2019/4/1.
 */

public class FeedbackActivity extends BaseActivity {

    private EditText mContentEditText, mEmailEditText;
    private Button mSubmitButton;
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_feedback);
        setTitleName(R.string.activity_feedback_title);
    }

    @Override
    protected void initView() {
        super.initView();
        mContentEditText = findViewById(R.id.content_feedback_editText);
        mEmailEditText = findViewById(R.id.email_feedback_editText);
        mSubmitButton = findViewById(R.id.submit_feedback_button);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSubmitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.submit_feedback_button:
                doSubmit();
                break;
            default:
                break;
        }
    }

    private void doSubmit() {
        Feedback feedback = new Feedback();
        String content = mContentEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        if (TextUtil.isEmpty(email) || TextUtil.isEmpty(content)) {
            ToastUtil.show(this, R.string.activity_feedback_error, Toast.LENGTH_SHORT, false);
            return;
        }
        if (!isEmail(email)) {
            ToastUtil.show(this, R.string.activity_feedback_email_error, Toast.LENGTH_SHORT, false);
            return;
        }
        feedback.setContent(content);
        feedback.setEmail(email);
        feedback.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastUtil.show(FeedbackActivity.this, R.string.activity_feedback_success, Toast.LENGTH_SHORT, true);
                    finish();
                } else {
                    e.printStackTrace();
                    ToastUtil.show(FeedbackActivity.this, R.string.activity_feedback_fail, Toast.LENGTH_SHORT, false);
                }
            }
        });
    }

    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

}
