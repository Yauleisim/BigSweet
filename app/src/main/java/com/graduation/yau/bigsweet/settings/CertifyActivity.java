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
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.model.Certification;
import com.graduation.yau.bigsweet.model.Seller;
import com.graduation.yau.bigsweet.model.User;
import com.graduation.yau.bigsweet.util.AvatarUtil;
import com.graduation.yau.bigsweet.util.DialogUtil;
import com.graduation.yau.bigsweet.util.PermissionHelper;
import com.graduation.yau.bigsweet.util.PermissionListener;
import com.graduation.yau.bigsweet.util.TextUtil;
import com.graduation.yau.bigsweet.util.ToastUtil;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import static com.graduation.yau.bigsweet.settings.UserMessageActivity.hasSdcard;

/**
 * Created by YAULEISIM on 2019/5/4.
 */

public class CertifyActivity extends BaseActivity {

    private ConstraintLayout mTypeConstraintLayout;
    private EditText mNameEditText, mNumberEditText;
    private TextView mTypeTextView;
    private ImageView mPicImageView;
    private Button mSubmitButton;
    private int mOpenChoice = 0, mTypeChoice = 0;
    private boolean isType;

    private PermissionHelper mPermissionHelper;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private String filePath = Environment.getExternalStorageDirectory().getPath() + "/photo.jpg";
    private String fileCropPath = Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg";
    private File fileUri = new File(filePath);
    private File fileCropUri = new File(fileCropPath);
    private Uri imageUri;
    private Uri cropImageUri;

    private String mCertificationPicUrl, mCertificationName, mCertificationType, mCertificationNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_certify);
        setTitleName(R.string.activity_certify_title);
    }

    @Override
    protected void initView() {
        super.initView();
        mTypeConstraintLayout = findViewById(R.id.type_certify_constraintLayout);
        mNameEditText = findViewById(R.id.name_certify_editText);
        mNumberEditText = findViewById(R.id.number_certify_editText);
        mPicImageView = findViewById(R.id.pic_certify_imageView);
        mSubmitButton = findViewById(R.id.submit_certify_button);
        mTypeTextView = findViewById(R.id.type_certify_textView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mTypeConstraintLayout.setOnClickListener(this);
        mPicImageView.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.type_certify_constraintLayout:
                isType = true;
                String[] item = {"居民身份证", "港澳居民往来内地通行证", "台湾居民往来大陆通行证"};
                DialogUtil.showSingleChoiceDialog(this, item, R.string.activity_certify_dialog_title, choiceListener, typePositiveListener);
                break;
            case R.id.pic_certify_imageView:
                isType = false;
                String[] items = {"拍照", "相册"};
                DialogUtil.showSingleChoiceDialog(this, items, R.string.activity_person_dialog_title, choiceListener, openPositiveListener);
                break;
            case R.id.submit_certify_button:
                doSubmit();
                break;
            default:
                break;
        }
    }

    private DialogInterface.OnClickListener choiceListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (isType) {
                mTypeChoice = which;
            } else {
                mOpenChoice = which;
            }
        }
    };

    private DialogInterface.OnClickListener typePositiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (mTypeChoice) {
                case 0:
                    mTypeTextView.setText("居民身份证");
                    break;
                case 1:
                    mTypeTextView.setText("港澳居民往来内地通行证");
                    break;
                case 2:
                    mTypeTextView.setText("台湾居民往来大陆通行证");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mPermissionHelper != null) {
            mPermissionHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private DialogInterface.OnClickListener openPositiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (mOpenChoice == 0) {
                // 拍照
                mPermissionHelper = new PermissionHelper(CertifyActivity.this);
                mPermissionHelper.requestPermissions(getString(R.string.toast_permission_write), new PermissionListener() {
                    @Override
                    public void onPermissionGranted(String... permissions) {
                        // 权限申请成功的回调
                        if (hasSdcard()) {
                            imageUri = Uri.fromFile(fileUri);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                //通过FileProvider创建一个content类型的Uri
                                imageUri = FileProvider.getUriForFile(CertifyActivity.this, "com.graduation.yau.bigsweet.fileprovider", fileUri);
                            }
                            AvatarUtil.takePicture(CertifyActivity.this, imageUri, CODE_CAMERA_REQUEST);
                        } else {
                            ToastUtil.show(CertifyActivity.this, R.string.toast_permission_error, Toast.LENGTH_SHORT, false);
                        }
                    }

                    @Override
                    public void onPermissionDenied(String... permissions) {
                        // 拒绝权限的回调
                        ToastUtil.show(CertifyActivity.this, R.string.toast_permission_deny, Toast.LENGTH_LONG, false);
                    }
                }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            } else if (mOpenChoice == 1) {
                // 相册
                mPermissionHelper = new PermissionHelper(CertifyActivity.this);
                mPermissionHelper.requestPermissions(getString(R.string.toast_permission_read), new PermissionListener() {
                    @Override
                    public void onPermissionGranted(String... permissions) {
                        // 权限申请成功的回调
                        AvatarUtil.openPic(CertifyActivity.this, CODE_GALLERY_REQUEST);
                    }

                    @Override
                    public void onPermissionDenied(String... permissions) {
                        // 拒绝权限的回调
                        ToastUtil.show(CertifyActivity.this, R.string.toast_permission_deny, Toast.LENGTH_LONG, false);
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int output_X = 480, output_Y = 480;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    cropImageUri = Uri.fromFile(fileCropUri);
                    AvatarUtil.cropImageUri(CertifyActivity.this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(AvatarUtil.getPath(CertifyActivity.this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(CertifyActivity.this, "com.graduation.yau.bigsweet.fileprovider", new File(newUri.getPath()));
                        AvatarUtil.cropImageUri(CertifyActivity.this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    } else {
                        ToastUtil.show(CertifyActivity.this, R.string.toast_permission_error, Toast.LENGTH_SHORT, false);
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = AvatarUtil.getBitmapFromUri(cropImageUri, CertifyActivity.this);
                    if (bitmap != null) {
                        showImages(bitmap);
                    }
                    break;
            }
        }
    }

    private void showImages(Bitmap bitmap) {
        Glide.with(this).load(bitmap).into(mPicImageView);
        // 上传至后台
        final BmobFile bmobFile = new BmobFile(fileCropUri);
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    mCertificationPicUrl = bmobFile.getFileUrl();
                } else {
                    e.printStackTrace();
                    ToastUtil.show(CertifyActivity.this, R.string.activity_certify_error, Toast.LENGTH_SHORT, false);
                }

            }
        });
    }

    private void doSubmit() {
        mCertificationName = mNameEditText.getText().toString();
        mCertificationNumber = mNumberEditText.getText().toString();
        mCertificationType = mTypeTextView.getText().toString();
        if (TextUtil.isEmpty(mCertificationName) || TextUtil.isEmpty(mCertificationNumber)
                || TextUtil.isEmpty(mCertificationPicUrl) || mCertificationType.equals("请选择证件类型")) {
            ToastUtil.show(this, R.string.activity_feedback_error, Toast.LENGTH_SHORT, false);
            return;
        }
        Certification certification = new Certification();
        certification.setName(mCertificationName);
        certification.setNumber(mCertificationNumber);
        certification.setType(mCertificationType);
        certification.setPicture(mCertificationPicUrl);
        certification.setUserId(BmobUser.getCurrentUser(User.class).getObjectId());
        certification.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastUtil.show(CertifyActivity.this, R.string.activity_feedback_success, Toast.LENGTH_SHORT, true);
                    addSeller();
                    finish();
                } else {
                    e.printStackTrace();
                    ToastUtil.show(CertifyActivity.this, R.string.activity_feedback_fail, Toast.LENGTH_SHORT, false);
                }
            }
        });
    }

    private void addSeller() {
        Seller seller = new Seller();
        seller.setAuthentication(false);
        User user = BmobUser.getCurrentUser(User.class);
        seller.setAvatarUrl(user.getAvatarUrl());
        seller.setName(user.getUsername() + "的店铺");
        seller.setUserId(user.getObjectId());
        seller.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e != null) {
                    e.printStackTrace();
                }
            }
        });
    }
}
