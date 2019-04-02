package com.graduation.yau.bigsweet.settings;

import android.os.Bundle;
import android.view.View;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.util.StartActivityUtil;
import com.graduation.yau.bigsweet.base.BaseActivity;

/**
 * Created by YAULEISIM on 2019/3/28.
 */

public class SettingsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_settings);
        setTitleName(R.string.activity_settings_title);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        findViewById(R.id.accountAndSecurity_settings_constraintLayout).setOnClickListener(this);
        findViewById(R.id.user_message_settings_constraintLayout).setOnClickListener(this);
        findViewById(R.id.about_settings_constraintLayout).setOnClickListener(this);
        findViewById(R.id.certification_settings_constraintLayout).setOnClickListener(this);
        findViewById(R.id.sign_out_settings_constraintLayout).setOnClickListener(this);
        findViewById(R.id.feedback_settings_constraintLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.user_message_settings_constraintLayout:
                StartActivityUtil.go(SettingsActivity.this, UserMessageActivity.class);
                break;
            case R.id.accountAndSecurity_settings_constraintLayout:
                StartActivityUtil.go(SettingsActivity.this, AccountSecurityActivity.class);
                break;
            case R.id.certification_settings_constraintLayout:
                break;
            case R.id.about_settings_constraintLayout:
                break;
            case R.id.feedback_settings_constraintLayout:
                StartActivityUtil.go(SettingsActivity.this, FeedbackActivity.class);
                break;
            case R.id.sign_out_settings_constraintLayout:
                break;
            default:
                break;
        }
    }
}
