package com.graduation.yau.bigsweet.settings;

import android.os.Bundle;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.base.BaseActivity;

/**
 * Created by YAULEISIM on 2019/4/2.
 */

public class UserMessageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_user_message);
        setTitleName(R.string.activity_user_msg_title);
    }
}
