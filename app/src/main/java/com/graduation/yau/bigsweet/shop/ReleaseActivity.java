package com.graduation.yau.bigsweet.shop;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.model.Product;
import com.graduation.yau.bigsweet.model.Seller;
import com.graduation.yau.bigsweet.model.User;
import com.graduation.yau.bigsweet.util.ConvertUtil;
import com.graduation.yau.bigsweet.util.DialogUtil;
import com.graduation.yau.bigsweet.util.TextUtil;
import com.graduation.yau.bigsweet.util.ToastUtil;
import com.winfo.photoselector.PhotoSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by YAULEISIM on 2019/5/4.
 */

public class ReleaseActivity extends BaseActivity {

    private EditText mProductNameEditText, mProductPriceEditText, mProductDescribeEditText;
    private EditText mTagOneEditText, mTagTwoEditText, mTagThreeEditText;
    private ConstraintLayout mClassifyConstraintLayout;
    private TextView mTypeTextView;
    private ImageView mPicOneImageView, mPicTwoImageView, mPicThreeImageView;
    private Button mSubmitButton;

    private int maxSelectCount;
    private ArrayList<String> mImageArrayList;
    private ArrayList<String> mImagePathList = new ArrayList<>(), mImageUrlList = new ArrayList<>();
    private static final int RESULT_OK = 1;

    private int mClassifyChoice = 0;
    private String[] items = {"烘培", "糖水", "水果", "饮品", "零食", "调料", "礼盒", "器皿", "其他"};

    private String mProductName, mProductPrice, mProductDescribe, mTag1, mTag2, mTag3, mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_release);
        setTitleName(R.string.activity_release_title);
    }

    @Override
    protected void initView() {
        super.initView();
        mProductNameEditText = findViewById(R.id.name_release_editText);
        mProductPriceEditText = findViewById(R.id.price_release_editText);
        mProductDescribeEditText = findViewById(R.id.describe_release_editText);

        mTagOneEditText = findViewById(R.id.tag1_release_editText);
        mTagTwoEditText = findViewById(R.id.tag2_release_editText);
        mTagThreeEditText = findViewById(R.id.tag3_release_editText);

        mClassifyConstraintLayout = findViewById(R.id.type_release_constraintLayout);
        mTypeTextView = findViewById(R.id.type_release_textView);

        mPicOneImageView = findViewById(R.id.pic1_release_imageView);
        mPicTwoImageView = findViewById(R.id.pic2_release_imageView);
        mPicThreeImageView = findViewById(R.id.pic3_release_imageView);

        mSubmitButton = findViewById(R.id.release_release_button);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mClassifyConstraintLayout.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);
        mPicOneImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.type_release_constraintLayout:
                DialogUtil.showSingleChoiceDialog(this, items, R.string.activity_release_single_dialog_title, choiceListener, positiveListener);
                break;
            case R.id.release_release_button:
                doRelease();
                break;
            case R.id.pic1_release_imageView:
                maxSelectCount = 3;
                selectPicture(maxSelectCount);
                break;
            case R.id.pic2_release_imageView:
                maxSelectCount = 2;
                selectPicture(maxSelectCount);
                break;
            case R.id.pic3_release_imageView:
                maxSelectCount = 1;
                selectPicture(maxSelectCount);
                break;
            default:
                break;
        }
    }

    private DialogInterface.OnClickListener choiceListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mClassifyChoice = which;
        }
    };

    private DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mTypeTextView.setText(items[mClassifyChoice]);
        }
    };

    private void selectPicture(int max) {
        PhotoSelector.builder()
                .setShowCamera(true)//显示拍照
                .setMaxSelectCount(max)//最大选择9 默认9，如果这里设置为-1则是不限数量
                .setGridColumnCount(3)//列数
                .setMaterialDesign(true)//design风格
                .setToolBarColor(ContextCompat.getColor(this, R.color.title_text_color))//toolbar的颜色
                .setBottomBarColor(ContextCompat.getColor(this, R.color.title_text_color))//底部bottombar的颜色
                .setStatusBarColor(ContextCompat.getColor(this, R.color.bottom_navigation_unchecked))//状态栏的颜色
                .start(ReleaseActivity.this, RESULT_OK);//当前activity 和 requestCode，不传requestCode则默认为PhotoSelector.DEFAULT_REQUEST_CODE

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case RESULT_OK:
                    mImageArrayList = data.getStringArrayListExtra(PhotoSelector.SELECT_RESULT);
                    switch (maxSelectCount) {
                        case 1:
                            if (mImageArrayList.size() == 1) {
                                // 之前选了2张，再选了一张
                                Glide.with(this).load(mImageArrayList.get(0)).into(mPicThreeImageView);
                                mPicThreeImageView.setVisibility(View.VISIBLE);
                                mPicThreeImageView.setOnClickListener(null);
                                mPicOneImageView.setOnClickListener(null);
                                mPicTwoImageView.setOnClickListener(null);
                                mImagePathList.add(mImageArrayList.get(0));
                            }
                            break;
                        case 2:
                            switch (mImageArrayList.size()) {
                                case 1:
                                    // 之前已经有1张，又选了1张
                                    Glide.with(this).load(mImageArrayList.get(0)).into(mPicTwoImageView);
                                    mPicTwoImageView.setVisibility(View.VISIBLE);
                                    mPicThreeImageView.setImageResource(R.drawable.ic_add_picture);
                                    mPicThreeImageView.setVisibility(View.VISIBLE);
                                    mPicThreeImageView.setOnClickListener(this);
                                    mPicOneImageView.setOnClickListener(null);
                                    mPicTwoImageView.setOnClickListener(null);
                                    mImagePathList.add(mImageArrayList.get(0));
                                    break;
                                case 2:
                                    // 又选了2张
                                    Glide.with(this).load(mImageArrayList.get(0)).into(mPicTwoImageView);
                                    Glide.with(this).load(mImageArrayList.get(1)).into(mPicThreeImageView);
                                    mPicTwoImageView.setVisibility(View.VISIBLE);
                                    mPicThreeImageView.setVisibility(View.VISIBLE);
                                    mPicOneImageView.setOnClickListener(null);
                                    mPicTwoImageView.setOnClickListener(null);
                                    mPicThreeImageView.setOnClickListener(null);
                                    mImagePathList.add(mImageArrayList.get(0));
                                    mImagePathList.add(mImageArrayList.get(1));
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 3:
                            switch (mImageArrayList.size()) {
                                case 3:
                                    // 一次选完了3张
                                    Glide.with(this).load(mImageArrayList.get(0)).into(mPicOneImageView);
                                    Glide.with(this).load(mImageArrayList.get(1)).into(mPicTwoImageView);
                                    Glide.with(this).load(mImageArrayList.get(2)).into(mPicThreeImageView);
                                    mPicTwoImageView.setVisibility(View.VISIBLE);
                                    mPicThreeImageView.setVisibility(View.VISIBLE);
                                    mPicOneImageView.setOnClickListener(null);
                                    mPicTwoImageView.setOnClickListener(null);
                                    mPicThreeImageView.setOnClickListener(null);
                                    mImagePathList.add(mImageArrayList.get(0));
                                    mImagePathList.add(mImageArrayList.get(1));
                                    mImagePathList.add(mImageArrayList.get(2));
                                    break;
                                case 2:
                                    // 选了2张
                                    Glide.with(this).load(mImageArrayList.get(0)).into(mPicOneImageView);
                                    Glide.with(this).load(mImageArrayList.get(1)).into(mPicTwoImageView);
                                    mPicTwoImageView.setVisibility(View.VISIBLE);
                                    mPicThreeImageView.setImageResource(R.drawable.ic_add_picture);
                                    mPicThreeImageView.setVisibility(View.VISIBLE);
                                    mPicThreeImageView.setOnClickListener(this);
                                    mPicOneImageView.setOnClickListener(null);
                                    mPicTwoImageView.setOnClickListener(null);
                                    mImagePathList.add(mImageArrayList.get(0));
                                    mImagePathList.add(mImageArrayList.get(1));
                                    break;
                                case 1:
                                    // 选了1张
                                    Glide.with(this).load(mImageArrayList.get(0)).into(mPicOneImageView);
                                    mPicTwoImageView.setImageResource(R.drawable.ic_add_picture);
                                    mPicTwoImageView.setVisibility(View.VISIBLE);
                                    mPicThreeImageView.setVisibility(View.INVISIBLE);
                                    mPicTwoImageView.setOnClickListener(this);
                                    mPicOneImageView.setOnClickListener(null);
                                    mPicThreeImageView.setOnClickListener(null);
                                    mImagePathList.add(mImageArrayList.get(0));
                                    break;
                                default:
                                    break;
                            }
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void doRelease() {
        mType = mTypeTextView.getText().toString();

        mTag1 = mTagOneEditText.getText().toString();
        mTag2 = mTagTwoEditText.getText().toString();
        mTag3 = mTagThreeEditText.getText().toString();

        mProductName = mProductNameEditText.getText().toString();
        mProductPrice = mProductPriceEditText.getText().toString();
        mProductDescribe = mProductDescribeEditText.getText().toString();

        if (TextUtil.isEmpty(mType) || TextUtil.isEmpty(mTag1) || TextUtil.isEmpty(mTag2)
                || TextUtil.isEmpty(mTag3) || TextUtil.isEmpty(mProductName) || TextUtil.isEmpty(mProductPrice)
                || TextUtil.isEmpty(mProductDescribe) || mImagePathList.size() != 3) {
            ToastUtil.show(this, R.string.activity_feedback_error, Toast.LENGTH_SHORT, false);
            return;
        }

        DialogUtil.showWaitingDialog(this, R.string.activity_release_dialog_title, R.string.activity_post_dialog_content);
        final Product product = new Product();
        product.setSale(0);
        product.setClassification(mType);
        product.setDescribe(mProductDescribe);
        product.setTitle(mProductName);
        product.setPrice(ConvertUtil.stringToInt(mProductPrice));
        product.setTagOne(mTag1);
        product.setTagTwo(mTag2);
        product.setTagThree(mTag3);

        BmobQuery<Seller> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("userId", BmobUser.getCurrentUser(User.class).getObjectId());
        bmobQuery.findObjects(new FindListener<Seller>() {
            @Override
            public void done(List<Seller> object, BmobException e) {
                if (e == null) {
                    product.setSellerId(object.get(0).getObjectId());
                } else {
                    e.printStackTrace();
                }
            }
        });

        for (int i = 0; i < mImagePathList.size(); i++) {
            uploadPic(mImagePathList.get(i), product);
        }
    }

    private void uploadPic(String path, final Product product) {
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e != null) {
                    e.printStackTrace();
                    DialogUtil.dismissWaitingDialog();
                    ToastUtil.show(ReleaseActivity.this, R.string.activity_release_fail, Toast.LENGTH_SHORT, false);
                } else {
                    mImageUrlList.add(bmobFile.getFileUrl());
                    if (mImageUrlList.size() == mImagePathList.size()) {
                        product.setPictureOneUrl(mImageUrlList.get(0));
                        product.setPictureTwoUrl(mImageUrlList.get(1));
                        product.setPictureThreeUrl(mImageUrlList.get(2));
                        saveProduct(product);
                    }
                }
            }
        });
    }

    private void saveProduct(Product product) {
        product.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    DialogUtil.dismissWaitingDialog();
                    ToastUtil.show(ReleaseActivity.this, R.string.activity_release_success, Toast.LENGTH_SHORT, true);
                    finish();
                } else {
                    e.printStackTrace();
                    DialogUtil.dismissWaitingDialog();
                    ToastUtil.show(ReleaseActivity.this, R.string.activity_release_fail, Toast.LENGTH_SHORT, false);
                }
            }
        });
    }
}
