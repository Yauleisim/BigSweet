package com.graduation.yau.bigsweet.util;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;

import com.graduation.yau.bigsweet.R;


/**
 * Created by YAULEISIM on 2019/4/12.
 */

public class DialogUtil {

    public static void showNormalDialog(Context context, int title, int content, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(R.string.dialog_normal_positive, positiveListener)
                .setNegativeButton(R.string.dialog_normal_negative, negativeListener);
        AlertDialog normalDialog = dialogBuilder.create();
        normalDialog.show();
        normalDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
        normalDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }
}
