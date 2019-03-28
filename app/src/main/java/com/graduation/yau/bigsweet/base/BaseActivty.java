package com.graduation.yau.bigsweet.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by YAULEISIM on 2019/3/28.
 */

public class BaseActivty extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLayout();
        initView();
        initEvent();
    }

    protected void loadLayout() {

    }

    protected void initView() {

    }

    protected void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }
}
