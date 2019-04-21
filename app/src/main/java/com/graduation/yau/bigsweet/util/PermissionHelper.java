package com.graduation.yau.bigsweet.util;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.graduation.yau.bigsweet.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by YAULEISIM on 2019/4/21.
 */

/*
使用说明:
可以在Activity或者Fragment中直接使用
    (1)申请权限
    PermissionHelper mPermissionHelper = new PermissionHelper(this);
    mPermissionHelper.requestPermissions("请授予xx[相机]，[读写]权限！", new PermissionListener() {
        @Override
        public void onPermissionGranted(String... permissions) {
            // 权限申请成功的回调
        }
        @Override
        public void onPermissionDenied(String... permissions) {
            // 拒绝权限的回调
        }
     }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    (2)处理权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if (mPermissionHelper != null) {
                mPermissionHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
            }
    }
*/
@SuppressWarnings("unused")
public class PermissionHelper {
    private static final int REQUEST_PERMISSION_CODE = 1000;
    private Object mContext;
    private PermissionListener mListener;
    private List<String> mPermissionList;

    public PermissionHelper(@NonNull Object context) {
        checkCallingObjectSuitability(context);
        mContext = context;
    }

    /**
     * 权限授权申请
     *
     * @param hintMessage 要申请的权限的提示
     * @param permissions 要申请的权限
     * @param listener    申请成功之后的callback
     */
    public void requestPermissions(@NonNull CharSequence hintMessage,
                                   @Nullable final PermissionListener listener,
                                   @NonNull final String... permissions) {
        if (listener != null) {
            mListener = listener;
        }
        mPermissionList = Arrays.asList(permissions);
        // 没全部权限
        if (!hasPermissions(mContext, permissions)) {
            // 需要向用户解释为什么申请这个权限
            boolean shouldShowRationale = false;
            for (String perm : permissions) {
                shouldShowRationale = shouldShowRationale || shouldShowRequestPermissionRationale(mContext, perm);
            }
            if (shouldShowRationale) {
                showMessageOKCancel(hintMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executePermissionsRequest(mContext, permissions,
                                REQUEST_PERMISSION_CODE);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                            mListener.onPermissionDenied(permissions);
                        }
                    }
                });
            } else {
                executePermissionsRequest(mContext, permissions,
                        REQUEST_PERMISSION_CODE);
            }
        } else if (mListener != null) { // 有全部权限
            mListener.onPermissionGranted(permissions);
        }
    }

    /**
     * 判断是否具有某权限
     */
    private boolean hasPermissions(@NonNull Object object, @NonNull String... perms) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (getActivity(object) == null) {
            return false;
        }
        for (String perm : perms) {
            boolean hasPerm = (ContextCompat.checkSelfPermission(getActivity(object), perm) ==
                    PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                return false;
            }
        }
        return true;
    }

    /**
     * 用来给外界判断是否有某项权限
     * <p>
     * 判断是否具有某权限
     *
     * @param context 传入上下文环境
     * @param perms   权限列表
     * @return true or false
     */
    public static boolean hasPermissions(@NonNull Context context, @NonNull String... perms) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String perm : perms) {
            boolean hasPerm = (ContextCompat.checkSelfPermission(context, perm) ==
                    PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                return false;
            }
        }
        return true;
    }

    // 处理onRequestPermissionsResult
    public void handleRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                boolean allGranted = true;
                for (int grant : grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        allGranted = false;
                        break;
                    }
                }
                if (allGranted && mListener != null) {
                    mListener.onPermissionGranted((String[]) mPermissionList.toArray());
                } else if (!allGranted && mListener != null) {
                    mListener.onPermissionDenied((String[]) mPermissionList.toArray());
                }
                break;
        }
    }

    // 是否需要弹出提示框,兼容fragment
    @TargetApi(23)
    private boolean shouldShowRequestPermissionRationale(@NonNull Object object, @NonNull String perm) {
        if (object instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) object, perm);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else {
            return false;
        }
    }

    // 执行申请,兼容fragment
    @TargetApi(23)
    private void executePermissionsRequest(@NonNull Object object, @NonNull String[] perms, int requestCode) {
        if (object instanceof android.app.Activity) {
            ActivityCompat.requestPermissions((Activity) object, perms, requestCode);
        } else if (object instanceof android.support.v4.app.Fragment) {
            ((android.support.v4.app.Fragment) object).requestPermissions(perms, requestCode);
        } else if (object instanceof android.app.Fragment) {
            ((android.app.Fragment) object).requestPermissions(perms, requestCode);
        }
    }

    // 检查传递的context是否合法,不合法抛出异常
    private void checkCallingObjectSuitability(Object context) {
        if (context == null) {
            throw new NullPointerException("Activity or Fragment should not be null");
        }
        boolean isActivity = context instanceof android.app.Activity;
        boolean isSupportFragment = context instanceof android.support.v4.app.Fragment;
        boolean isAppFragment = context instanceof android.app.Fragment;
        if (!(isSupportFragment || isActivity || (isAppFragment && isNeedRequest()))) {
            if (isAppFragment) {
                throw new IllegalArgumentException(
                        "Target SDK needs to be greater than 23 if caller is android.app.Fragment");
            } else {
                throw new IllegalArgumentException("Caller must be an Activity or a Fragment.");
            }
        }
    }

    // 是否高于6.0版本
    private boolean isNeedRequest() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    @TargetApi(11)
    private Activity getActivity(@NonNull Object object) {
        if (object instanceof Activity) {
            return ((Activity) object);
        } else if (object instanceof android.support.v4.app.Fragment) {
            return ((android.support.v4.app.Fragment) object).getActivity();
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).getActivity();
        } else {
            return null;
        }
    }


    // 弹出提示框
    private void showMessageOKCancel(CharSequence message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(getActivity(mContext))
                .setMessage(message)
                .setPositiveButton(R.string.dialog_normal_positive, okListener)
                .setNegativeButton(R.string.dialog_normal_negative, cancelListener)
                .create()
                .show();
    }
}


