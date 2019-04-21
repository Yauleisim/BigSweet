package com.graduation.yau.bigsweet.settings;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.User;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.util.AvatarUtil;
import com.graduation.yau.bigsweet.util.DialogUtil;
import com.graduation.yau.bigsweet.util.PermissionHelper;
import com.graduation.yau.bigsweet.util.PermissionListener;
import com.graduation.yau.bigsweet.util.TextUtil;
import com.graduation.yau.bigsweet.util.ToastUtil;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by YAULEISIM on 2019/4/2.
 */

public class UserMessageActivity extends BaseActivity {

    private EditText mNameEditText, mSignatureEditText;
    private RadioButton mMaleRadioButton, mFemaleRadioButton;
    private String mName, mSignature, mGender;
    private ImageView mAvatarImageView;

    private int mOpenChoice = 0;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    private PermissionHelper mPermissionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_user_message);
        setTitleName(R.string.activity_user_msg_title);
    }

    @Override
    protected void initView() {
        super.initView();
        mNameEditText = findViewById(R.id.name_user_message_editText);
        mSignatureEditText = findViewById(R.id.signature_user_message_editText);
        mMaleRadioButton = findViewById(R.id.male_user_message_radioButton);
        mFemaleRadioButton = findViewById(R.id.female_user_message_radioButton);
        mAvatarImageView = findViewById(R.id.avatar_user_message_imageView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        User currentUser = BmobUser.getCurrentUser(User.class);
        mName = currentUser.getUsername();
        if (!TextUtil.isEmpty(mName)) {
            mNameEditText.setText(mName);
        }
        mSignature = currentUser.getSignature();
        if (!TextUtil.isEmpty(mSignature)) {
            mSignatureEditText.setText(mSignature);
        }
        mGender = currentUser.getGender();
        if (!TextUtil.isEmpty(mGender) && mGender.equals(getString(R.string.activity_user_msg_female))) {
            mFemaleRadioButton.setChecked(true);
            mMaleRadioButton.setChecked(false);
            mGender = getString(R.string.activity_user_msg_female);
        } else {
            mFemaleRadioButton.setChecked(false);
            mMaleRadioButton.setChecked(true);
            mGender = getString(R.string.activity_user_msg_male);
        }

        findViewById(R.id.avatar_user_message_constraintLayout).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        doSave();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_title_common_imageView:
                doSave();
                break;
            case R.id.avatar_user_message_constraintLayout:
                String[] items = {"拍照", "相册"};
                DialogUtil.showSingleChoiceDialog(this, items, R.string.activity_person_dialog_title, choiceListener, positiveListener);
                break;
            default:
                break;
        }
    }

    private void doSave() {
        final User user = BmobUser.getCurrentUser(User.class);
        boolean flag = false;
        String name = mNameEditText.getText().toString();
        if (!TextUtil.equals(mName, name)) {
            user.setUsername(name);
            flag = true;
        } else {
            user.setUsername(null);
        }

        String signature = mSignatureEditText.getText().toString();
        if (!TextUtil.equals(signature, mSignature)) {
            user.setSignature(signature);
            flag = true;
        }
        if (mMaleRadioButton.isChecked() && mGender.equals(getString(R.string.activity_user_msg_female))) {
            user.setGender(getString(R.string.activity_user_msg_male));
            flag = true;
        } else if (mFemaleRadioButton.isChecked() && mGender.equals(getString(R.string.activity_user_msg_male))) {
            user.setGender(getString(R.string.activity_user_msg_female));
            flag = true;
        }
        if (!flag) {
            finish();
            return;
        }
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtil.show(UserMessageActivity.this, R.string.activity_user_msg_success, Toast.LENGTH_SHORT, true);
                    finish();
                } else {
                    e.printStackTrace();
                    ToastUtil.show(UserMessageActivity.this, R.string.activity_user_msg_fail, Toast.LENGTH_SHORT, false);
                }
            }
        });
    }

    private DialogInterface.OnClickListener choiceListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mOpenChoice = which;
        }
    };

    private DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (mOpenChoice == 0) {
                // 拍照
                mPermissionHelper = new PermissionHelper(UserMessageActivity.this);
                mPermissionHelper.requestPermissions(getString(R.string.toast_permission_write), new PermissionListener() {
                    @Override
                    public void onPermissionGranted(String... permissions) {
                        // 权限申请成功的回调
                        if (hasSdcard()) {
                            imageUri = Uri.fromFile(fileUri);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                //通过FileProvider创建一个content类型的Uri
                                imageUri = FileProvider.getUriForFile(UserMessageActivity.this, "com.graduation.yau.bigsweet.fileprovider", fileUri);
                            }
                            AvatarUtil.takePicture(UserMessageActivity.this, imageUri, CODE_CAMERA_REQUEST);
                        } else {
                            ToastUtil.show(UserMessageActivity.this, R.string.toast_permission_error, Toast.LENGTH_SHORT, false);
                        }
                    }

                    @Override
                    public void onPermissionDenied(String... permissions) {
                        // 拒绝权限的回调
                        ToastUtil.show(UserMessageActivity.this, R.string.toast_permission_deny, Toast.LENGTH_LONG, false);
                    }
                }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            } else if (mOpenChoice == 1) {
                // 相册
                mPermissionHelper = new PermissionHelper(UserMessageActivity.this);
                mPermissionHelper.requestPermissions(getString(R.string.toast_permission_read), new PermissionListener() {
                    @Override
                    public void onPermissionGranted(String... permissions) {
                        // 权限申请成功的回调
                        AvatarUtil.openPic(UserMessageActivity.this, CODE_GALLERY_REQUEST);
                    }

                    @Override
                    public void onPermissionDenied(String... permissions) {
                        // 拒绝权限的回调
                        ToastUtil.show(UserMessageActivity.this, R.string.toast_permission_deny, Toast.LENGTH_LONG, false);
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    };

    /**
     * 检查设备是否存在SDCard的工具方法
     */

    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mPermissionHelper != null) {
            mPermissionHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int output_X = 480, output_Y = 480;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    cropImageUri = Uri.fromFile(fileCropUri);
                    AvatarUtil.cropImageUri(UserMessageActivity.this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(AvatarUtil.getPath(UserMessageActivity.this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(UserMessageActivity.this, "com.graduation.yau.bigsweet.fileprovider", new File(newUri.getPath()));
                        AvatarUtil.cropImageUri(UserMessageActivity.this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    } else {
                        ToastUtil.show(UserMessageActivity.this, R.string.toast_permission_error, Toast.LENGTH_SHORT, false);
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = AvatarUtil.getBitmapFromUri(cropImageUri, UserMessageActivity.this);
                    if (bitmap != null) {
                        showImages(bitmap);
                    }
                    break;
            }
        }
    }

    private void showImages(Bitmap bitmap) {
        mAvatarImageView.setImageBitmap(bitmap);
    }
}
