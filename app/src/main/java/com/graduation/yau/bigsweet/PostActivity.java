package com.graduation.yau.bigsweet;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.util.DialogUtil;
import com.winfo.photoselector.PhotoSelector;

import java.util.ArrayList;

/**
 * Created by YAULEISIM on 2019/3/24.
 */

public class PostActivity extends BaseActivity {

    private static final int RESULT_OK = 1;
    private ArrayList<String> mImageArrayList;
    private ImageView mAddImageView, mOneImageView, mTwoImageView;
    private TextView mPublicResultTextView, mTopicResultTextView;
    private int mPublicChoice = 0;
    private int maxSelectCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_post);
        hideTitle();
    }

    @Override
    protected void initView() {
        super.initView();
        mAddImageView = findViewById(R.id.add_picture_post_imageView);
        mOneImageView = findViewById(R.id.one_picture_post_imageView);
        mTwoImageView = findViewById(R.id.two_picture_post_imageView);
        mPublicResultTextView = findViewById(R.id.public_result_post_textView);
        mTopicResultTextView = findViewById(R.id.topic_result_post_textView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        findViewById(R.id.back_post_imageView).setOnClickListener(this);
        findViewById(R.id.public_post_constraintLayout).setOnClickListener(this);
        findViewById(R.id.topic_post_constraintLayout).setOnClickListener(this);
        findViewById(R.id.location_post_constraintLayout).setOnClickListener(this);
        findViewById(R.id.send_post_button).setOnClickListener(this);
        mAddImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.back_post_imageView:
                finish();
                break;
            case R.id.send_post_button:
                // 发贴
                doPost();
                break;
            case R.id.location_post_constraintLayout:
                break;
            case R.id.topic_post_constraintLayout:
                showInputDialog(this, R.string.activity_post_topic);
                break;
            case R.id.public_post_constraintLayout:
                String[] items = {"公开", "私密"};
                DialogUtil.showSingleChoiceDialog(this, items, R.string.activity_post_public, choiceListener, publicPositiveListener);
                break;
            case R.id.add_picture_post_imageView:
                maxSelectCount = 3;
                selectPicture(maxSelectCount);
                break;
            case R.id.one_picture_post_imageView:
                maxSelectCount = 2;
                selectPicture(maxSelectCount);
                break;
            case R.id.two_picture_post_imageView:
                maxSelectCount = 1;
                selectPicture(maxSelectCount);
                break;
            default:
                break;
        }
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
                                Glide.with(this).load(mImageArrayList.get(0)).into(mTwoImageView);
                                mTwoImageView.setVisibility(View.VISIBLE);
                                mTwoImageView.setOnClickListener(null);
                                mAddImageView.setOnClickListener(null);
                                mOneImageView.setOnClickListener(null);
                            }
                            break;
                        case 2:
                            switch (mImageArrayList.size()) {
                                case 1:
                                    // 之前已经有1张，又选了1张
                                    Glide.with(this).load(mImageArrayList.get(0)).into(mOneImageView);
                                    mOneImageView.setVisibility(View.VISIBLE);
                                    mTwoImageView.setImageResource(R.drawable.ic_add_picture);
                                    mTwoImageView.setVisibility(View.VISIBLE);
                                    mTwoImageView.setOnClickListener(this);
                                    mAddImageView.setOnClickListener(null);
                                    mOneImageView.setOnClickListener(null);
                                    break;
                                case 2:
                                    // 又选了2张
                                    Glide.with(this).load(mImageArrayList.get(0)).into(mOneImageView);
                                    Glide.with(this).load(mImageArrayList.get(1)).into(mTwoImageView);
                                    mOneImageView.setVisibility(View.VISIBLE);
                                    mTwoImageView.setVisibility(View.VISIBLE);
                                    mAddImageView.setOnClickListener(null);
                                    mOneImageView.setOnClickListener(null);
                                    mTwoImageView.setOnClickListener(null);
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 3:
                            switch (mImageArrayList.size()) {
                                case 3:
                                    // 一次选完了3张
                                    Glide.with(this).load(mImageArrayList.get(0)).into(mAddImageView);
                                    Glide.with(this).load(mImageArrayList.get(1)).into(mOneImageView);
                                    Glide.with(this).load(mImageArrayList.get(2)).into(mTwoImageView);
                                    mOneImageView.setVisibility(View.VISIBLE);
                                    mTwoImageView.setVisibility(View.VISIBLE);
                                    mAddImageView.setOnClickListener(null);
                                    mOneImageView.setOnClickListener(null);
                                    mTwoImageView.setOnClickListener(null);
                                    break;
                                case 2:
                                    // 选了2张
                                    Glide.with(this).load(mImageArrayList.get(0)).into(mAddImageView);
                                    Glide.with(this).load(mImageArrayList.get(1)).into(mOneImageView);
                                    mOneImageView.setVisibility(View.VISIBLE);
                                    mTwoImageView.setImageResource(R.drawable.ic_add_picture);
                                    mTwoImageView.setVisibility(View.VISIBLE);
                                    mTwoImageView.setOnClickListener(this);
                                    mAddImageView.setOnClickListener(null);
                                    mOneImageView.setOnClickListener(null);
                                    break;
                                case 1:
                                    // 选了1张
                                    Glide.with(this).load(mImageArrayList.get(0)).into(mAddImageView);
                                    mOneImageView.setImageResource(R.drawable.ic_add_picture);
                                    mOneImageView.setVisibility(View.VISIBLE);
                                    mTwoImageView.setVisibility(View.INVISIBLE);
                                    mOneImageView.setOnClickListener(this);
                                    mAddImageView.setOnClickListener(null);
                                    mTwoImageView.setOnClickListener(null);
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

    private void selectPicture(int max) {
        PhotoSelector.builder()
                .setShowCamera(true)//显示拍照
                .setMaxSelectCount(max)//最大选择9 默认9，如果这里设置为-1则是不限数量
                .setGridColumnCount(3)//列数
                .setMaterialDesign(true)//design风格
                .setToolBarColor(ContextCompat.getColor(this, R.color.title_text_color))//toolbar的颜色
                .setBottomBarColor(ContextCompat.getColor(this, R.color.title_text_color))//底部bottombar的颜色
                .setStatusBarColor(ContextCompat.getColor(this, R.color.bottom_navigation_unchecked))//状态栏的颜色
                .start(PostActivity.this, RESULT_OK);//当前activity 和 requestCode，不传requestCode则默认为PhotoSelector.DEFAULT_REQUEST_CODE

    }

    private DialogInterface.OnClickListener choiceListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mPublicChoice = which;
        }
    };

    private DialogInterface.OnClickListener publicPositiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (mPublicChoice == 0) {
                // 公开
                mPublicResultTextView.setVisibility(View.VISIBLE);
                mPublicResultTextView.setText("公开");
            } else if (mPublicChoice == 1) {
                // 私密
                mPublicResultTextView.setVisibility(View.VISIBLE);
                mPublicResultTextView.setText("私密");
            }
        }
    };

    private void showInputDialog(final Context context, int title) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_input_topic, null);
        final EditText editText = view.findViewById(R.id.topic_dialog_input_topic_editText);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(view)
                .setPositiveButton(R.string.dialog_normal_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTopicResultTextView.setVisibility(View.VISIBLE);
                        mTopicResultTextView.setText(editText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.dialog_normal_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog inputDialog = dialogBuilder.create();
        inputDialog.show();
        inputDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
        inputDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }

    private void doPost() {
        // 文字内容存下来
        // 照片上传，存url
        // 谁可以看的结果存下来
        // 参与话题存下来
    }

}
