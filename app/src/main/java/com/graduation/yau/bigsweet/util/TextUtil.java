package com.graduation.yau.bigsweet.util;

/**
 * Created by YAULEISIM on 2019/4/11.
 */

public class TextUtil {

    public static boolean isEmpty(String text) {
        if (text == null || text.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean equals(String one, String two) {
        if (isEmpty(one)) {
            if (isEmpty(two)) {
                return true;
            } else {
                return false;
            }
        } else {
            if (isEmpty(two)) {
                return false;
            } else {
                if (one.equals(two)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
}
