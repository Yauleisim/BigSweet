package com.graduation.yau.bigsweet;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.model.Post;
import com.graduation.yau.bigsweet.model.User;
import com.graduation.yau.bigsweet.util.TextUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by YAULEISIM on 2019/4/24.
 */

public class PostDetailActivity extends BaseActivity {

    private Post mPost;
    private ImageView mPosterAvatarImageView, mPictureOneImageView, mPictureTwoImageView, mPictureThreeImageView;
    private TextView mPosterNameTextView, mContentTextView, mLabelTextView, mPostTimeTextView;
    private ConstraintLayout mLabelConstraintLayout;
    private LinearLayout mPictureLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPost = (Post) getIntent().getSerializableExtra("post");
        if (mPost == null) {
            return;
        }
        loadContentLayout(R.layout.activity_post_detail);
        setTitleName(R.string.activity_post_detail_title);
    }

    @Override
    protected void initView() {
        super.initView();
        mPosterAvatarImageView = findViewById(R.id.avatar_post_detail_imageView);
        mPosterNameTextView = findViewById(R.id.name_post_detail_textView);
        mPostTimeTextView = findViewById(R.id.time_post_detail_textView);
        mContentTextView = findViewById(R.id.content_post_detail_textView);
        mLabelTextView = findViewById(R.id.label_post_detail_textView);
        mPictureOneImageView = findViewById(R.id.one_picture_post_detail_imageView);
        mPictureTwoImageView = findViewById(R.id.two_picture_post_detail_imageView);
        mPictureThreeImageView = findViewById(R.id.three_picture_post_detail_imageView);
        mLabelConstraintLayout = findViewById(R.id.label_post_detail_constraintLayout);
        mPictureLinearLayout = findViewById(R.id.picture_post_detail_linearLayout);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mContentTextView.setText(mPost.getContent());
        String label = mPost.getTopic();
        if (!TextUtil.isEmpty(label)) {
            mLabelTextView.setText(label);
        } else {
            mLabelConstraintLayout.setVisibility(View.GONE);
        }

        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        userBmobQuery.addWhereEqualTo("objectId", mPost.getUserId());
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 1) {
                        User poster = object.get(0);
                        mPosterNameTextView.setText(poster.getUsername());
                        mPostTimeTextView.setText(poster.getUpdatedAt());
                        Glide.with(PostDetailActivity.this).load(poster.getAvatarUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(mPosterAvatarImageView);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

        String picOneUrl = mPost.getPictureOne();
        if (!TextUtil.isEmpty(picOneUrl)) {
            Glide.with(this).load(picOneUrl).into(mPictureOneImageView);
        } else {
            mPictureOneImageView.setVisibility(View.INVISIBLE);
        }
        String picTwoUrl = mPost.getPictureTwo();
        if (!TextUtil.isEmpty(picTwoUrl)) {
            Glide.with(this).load(picTwoUrl).into(mPictureTwoImageView);
        } else {
            mPictureTwoImageView.setVisibility(View.INVISIBLE);
        }
        String picThreeUrl = mPost.getPictureThree();
        if (!TextUtil.isEmpty(picThreeUrl)) {
            Glide.with(this).load(picThreeUrl).into(mPictureThreeImageView);
        } else {
            mPictureThreeImageView.setVisibility(View.INVISIBLE);
        }
        if (TextUtil.isEmpty(picThreeUrl) && TextUtil.isEmpty(picTwoUrl) && TextUtil.isEmpty(picOneUrl)) {
            mPictureLinearLayout.setVisibility(View.GONE);
        }
    }
}
