package com.graduation.yau.bigsweet;

import android.os.Bundle;
import android.view.View;

import com.graduation.yau.bigsweet.base.BaseActivty;

/**
 * Created by YAULEISIM on 2019/3/28.
 */

public class SettingsActivity extends BaseActivty {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_settings);
        setTitle(R.string.activity_settings_title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_message_settings_constraintLayout:
                break;
            case R.id.accountAndSecurity_settings_constraintLayout:
                break;
            case R.id.certification_settings_constraintLayout:
                break;
            case R.id.about_settings_constraintLayout:
                break;
            case R.id.feedback_settings_constraintLayout:
                break;
            case R.id.sign_out_settings_constraintLayout:
                break;
            default:
                break;
        }
    }
}
