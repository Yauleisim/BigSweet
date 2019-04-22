package com.graduation.yau.bigsweet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.winfo.photoselector.PhotoSelector;

import java.util.ArrayList;

/**
 * Created by YAULEISIM on 2019/3/24.
 */

public class PostActivity extends BaseActivity {

    private static final int RESULT_OK = 1;
    private ArrayList<String> mImageArrayList;
    private ImageView mAddImageView, mOneImageView, mTwoImageView;

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
                break;
            case R.id.location_post_constraintLayout:
                break;
            case R.id.topic_post_constraintLayout:
                break;
            case R.id.public_post_constraintLayout:
                break;
            case R.id.add_picture_post_imageView:
                PhotoSelector.builder()
                        .setShowCamera(true)//显示拍照
                        .setMaxSelectCount(3)//最大选择9 默认9，如果这里设置为-1则是不限数量
                        .setGridColumnCount(3)//列数
                        .setMaterialDesign(true)//design风格
                        .setToolBarColor(ContextCompat.getColor(this, R.color.title_text_color))//toolbar的颜色
                        .setBottomBarColor(ContextCompat.getColor(this, R.color.title_text_color))//底部bottombar的颜色
                        .setStatusBarColor(ContextCompat.getColor(this, R.color.bottom_navigation_unchecked))//状态栏的颜色
                        .start(PostActivity.this, RESULT_OK);//当前activity 和 requestCode，不传requestCode则默认为PhotoSelector.DEFAULT_REQUEST_CODE
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
                    if (mImageArrayList.size() == 3) {
                        Glide.with(this).load(mImageArrayList.get(0)).into(mAddImageView);
                        Glide.with(this).load(mImageArrayList.get(1)).into(mOneImageView);
                        Glide.with(this).load(mImageArrayList.get(2)).into(mTwoImageView);
                        mOneImageView.setVisibility(View.VISIBLE);
                        mTwoImageView.setVisibility(View.VISIBLE);
                    } else if (mImageArrayList.size() == 2) {
                        Glide.with(this).load(mImageArrayList.get(0)).into(mAddImageView);
                        Glide.with(this).load(mImageArrayList.get(1)).into(mOneImageView);
                        mOneImageView.setVisibility(View.VISIBLE);
                        mTwoImageView.setImageResource(R.drawable.ic_add_picture);
                        mTwoImageView.setVisibility(View.VISIBLE);
                    } else if (mImageArrayList.size() == 1) {
                        Glide.with(this).load(mImageArrayList.get(0)).into(mAddImageView);
                        mOneImageView.setImageResource(R.drawable.ic_add_picture);
                        mOneImageView.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
