package com.graduation.yau.bigsweet.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.graduation.yau.bigsweet.R;


/**
 * Created by YAULEISIM on 2019/4/12.
 */

public class DialogUtil {

    private static ProgressDialog waitingDialog;

    public static void showNormalDialog(Context context, int title, int content, DialogInterface.OnClickListener positiveListener) {
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

    public static void showSingleChoiceDialog(Context context, String[] items, int title, DialogInterface.OnClickListener choiceListener, DialogInterface.OnClickListener positiveListener) {
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(context);
        singleChoiceDialog.setTitle(title);
        singleChoiceDialog.setSingleChoiceItems(items, 0, choiceListener);
        singleChoiceDialog.setPositiveButton(R.string.dialog_normal_positive, positiveListener);
        singleChoiceDialog.show();
    }

    private static DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };

    public static void showWaitingDialog(Context context, int title, int content) {
        waitingDialog = new ProgressDialog(context);
        waitingDialog.setTitle(title);
        waitingDialog.setMessage(context.getString(content));
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();
    }

    public static void dismissWaitingDialog() {
        if (waitingDialog == null) {
            return;
        }
        waitingDialog.dismiss();
    }
}
