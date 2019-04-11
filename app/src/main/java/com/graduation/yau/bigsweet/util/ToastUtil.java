package com.graduation.yau.bigsweet.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.yau.bigsweet.R;

/**
 * Created by YAULEISIM on 2019/4/11.
 */

public class ToastUtil {

    public static void show(Context context, String content, int duration, boolean yes) {
        //自定义Toast控件
        View toastView = LayoutInflater.from(context).inflate(R.layout.toast_common, null);
        TextView textView = (TextView) toastView.findViewById(R.id.text_toast_common_textView);
        textView.setText(content);
        ImageView imageView = toastView.findViewById(R.id.pic_toast_common_imageView);
        if (yes) {
            imageView.setImageResource(R.drawable.ic_toast_yes);
        } else {
            imageView.setImageResource(R.drawable.ic_toast_no);
        }
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(toastView);
        toast.show();
    }

}
