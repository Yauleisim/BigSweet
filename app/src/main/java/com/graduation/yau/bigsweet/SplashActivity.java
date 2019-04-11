package com.graduation.yau.bigsweet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.graduation.yau.bigsweet.login.LoginActivity;
import com.graduation.yau.bigsweet.util.SharedPreferencesUtil;
import com.graduation.yau.bigsweet.util.StartActivityUtil;

import cn.bmob.v3.Bmob;

/**
 * Created by YAULEISIM on 2019/4/11.
 */

public class SplashActivity extends AppCompatActivity {

    private static final int sleepTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        Bmob.initialize(this, "0d26730099af3b78fe7585ef0bc813cb");
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            public void run() {
                long start = System.currentTimeMillis();
                long costTime = System.currentTimeMillis() - start;
                //等待sleeptime时长
                if (sleepTime - costTime > 0) {
                    try {
                        Thread.sleep(sleepTime - costTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 进入主页面
                if (SharedPreferencesUtil.containsKey(getApplicationContext(), LoginActivity.KEY_LOGIN_STATE)
                        && SharedPreferencesUtil.get(getApplicationContext(), LoginActivity.KEY_LOGIN_STATE, false).equals(true)) {
                    StartActivityUtil.go(SplashActivity.this, MainActivity.class);
                } else {
                    StartActivityUtil.go(SplashActivity.this, LoginActivity.class);
                }
                finish();
            }
        }).start();
    }
}
