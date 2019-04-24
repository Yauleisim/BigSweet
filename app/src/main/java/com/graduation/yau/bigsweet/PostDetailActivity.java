package com.graduation.yau.bigsweet;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.model.Post;
import com.graduation.yau.bigsweet.model.User;
import com.graduation.yau.bigsweet.util.TextUtil;
import com.graduation.yau.bigsweet.util.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by YAULEISIM on 2019/4/24.
 */

public class PostDetailActivity extends BaseActivity {

    private Post mPost;
    private User mPoster;
    private ImageView mPosterAvatarImageView, mPictureOneImageView, mPictureTwoImageView, mPictureThreeImageView;
    private TextView mPosterNameTextView, mContentTextView, mLabelTextView, mPostTimeTextView, mFollowTextView;
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
        mFollowTextView = findViewById(R.id.follow_post_detail_textView);
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
        mPostTimeTextView.setText(mPost.getUpdatedAt());

        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        userBmobQuery.addWhereEqualTo("objectId", mPost.getUserId());
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 1) {
                        mPoster = object.get(0);
                        mPosterNameTextView.setText(mPoster.getUsername());
                        Glide.with(PostDetailActivity.this).load(mPoster.getAvatarUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(mPosterAvatarImageView);
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

        User currentUser = BmobUser.getCurrentUser(User.class);
        String followId = mPost.getUserId();
        if (currentUser.getObjectId().equals(followId)) {
            mFollowTextView.setVisibility(View.GONE);
        } else {
            List followList = currentUser.getFollowList();
            if (followList.size() != 0 && followList.contains(followId)) {
                mFollowTextView.setText(R.string.activity_post_detail_followed);
                mFollowTextView.setOnClickListener(null);
            } else {
                mFollowTextView.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.follow_post_detail_textView:
                doFollow();
                break;
            default:
                break;
        }
    }

    private void doFollow() {
        final User currentUser = BmobUser.getCurrentUser(User.class);
        currentUser.addAFollow(mPost.getUserId());
        currentUser.setUsername(null);
        currentUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtil.show(PostDetailActivity.this, R.string.activity_post_detail_success, Toast.LENGTH_SHORT, true);
                    mFollowTextView.setText(R.string.activity_post_detail_followed);
                    mFollowTextView.setOnClickListener(null);
                } else {
                    e.printStackTrace();
                    ToastUtil.show(PostDetailActivity.this, R.string.activity_post_detail_fail, Toast.LENGTH_SHORT, false);
                }
            }
        });
    }
}
