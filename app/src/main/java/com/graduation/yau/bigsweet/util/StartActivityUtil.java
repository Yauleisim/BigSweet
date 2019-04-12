package com.graduation.yau.bigsweet.util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by YAULEISIM on 2019/4/2.
 */

public class StartActivityUtil {

    public static void go(Context fromActivity, Class toActivity) {
        try {
            Intent mIntent = new Intent(fromActivity, toActivity);
            fromActivity.startActivity(mIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goWithFlag(Context fromActivity, Class toActivity, int flag) {
        try {
            Intent mIntent = new Intent(fromActivity, toActivity);
            mIntent.setFlags(flag);
            fromActivity.startActivity(mIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
