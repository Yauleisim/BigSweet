package com.graduation.yau.bigsweet.util;

/**
 * Created by YAULEISIM on 2019/4/21.
 */

public interface PermissionListener {
    // 同意
    void onPermissionGranted(String... permissions);

    // 拒绝
    void onPermissionDenied(String... permissions);
}
