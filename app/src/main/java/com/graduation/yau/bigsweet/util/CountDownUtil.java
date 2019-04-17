package com.graduation.yau.bigsweet.util;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.graduation.yau.bigsweet.R;

/**
 * Created by YAULEISIM on 2019/4/12.
 */

public class CountDownUtil extends CountDownTimer {

    private TextView mCountDownTextView;

    public CountDownUtil(long millisInFuture, long countDownInterval, TextView tv) {
        super(millisInFuture, countDownInterval);
        this.mCountDownTextView = tv;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mCountDownTextView.setText("剩余" + millisUntilFinished / 1000 + "秒");
    }

    @Override
    public void onFinish() {
        mCountDownTextView.setText(R.string.activity_register_with_phone_get_code);
        mCountDownTextView.setBackgroundResource(R.drawable.bg_post_button);
        mCountDownTextView.setClickable(true);
    }
}
