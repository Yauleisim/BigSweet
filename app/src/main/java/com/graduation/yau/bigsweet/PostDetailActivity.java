package com.graduation.yau.bigsweet;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.home.CommentAdapter;
import com.graduation.yau.bigsweet.model.Post;
import com.graduation.yau.bigsweet.model.User;
import com.graduation.yau.bigsweet.util.TextUtil;
import com.graduation.yau.bigsweet.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private ImageView mPosterAvatarImageView, mPictureOneImageView, mPictureTwoImageView, mPictureThreeImageView, mLikeImageView;
    private TextView mPosterNameTextView, mContentTextView, mLabelTextView, mPostTimeTextView, mFollowTextView, mLikeTextView;
    private ConstraintLayout mLabelConstraintLayout;
    private LinearLayout mPictureLinearLayout, mLikeLinearLayout, mCommentLinearLayout;
    private User currentUser;
    private RecyclerView mCommentRecyclerView;
    private CommentAdapter mCommentAdapter;
    private List<String[]> mCommentList;

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
        mLikeLinearLayout = findViewById(R.id.like_post_detail_linearLayout);
        mLikeImageView = findViewById(R.id.like_post_detail_imageView);
        mLikeTextView = findViewById(R.id.like_count_post_detail_textView);
        mCommentLinearLayout = findViewById(R.id.comment_post_detail_linearLayout);
        mCommentRecyclerView = findViewById(R.id.comment_post_detail_recyclerView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mCommentLinearLayout.setOnClickListener(this);

        mCommentAdapter = new CommentAdapter(this, mPost.getCommentList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCommentRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mCommentRecyclerView.setAdapter(mCommentAdapter);

        currentUser = BmobUser.getCurrentUser(User.class);
        mLikeLinearLayout.setOnClickListener(this);
        if (isCollect()) {
            mLikeImageView.setImageResource(R.drawable.ic_like_selected);
            mLikeTextView.setText(R.string.activity_post_detail_collected);
        } else {
            mLikeImageView.setImageResource(R.drawable.ic_like_count);
            mLikeTextView.setText(R.string.activity_post_detail_like);
        }

        mContentTextView.setText(mPost.getContent());
        String label = mPost.getTopic();
        if (!TextUtil.isEmpty(label)) {
            mLabelTextView.setText(label);
        } else {
            mLabelConstraintLayout.setVisibility(View.GONE);
        }
        mPostTimeTextView.setText(mPost.getCreatedAt());

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

        String followId = mPost.getUserId();
        if (currentUser.getObjectId().equals(followId)) {
            mFollowTextView.setVisibility(View.GONE);
        } else {
            if (isFollow()) {
                mFollowTextView.setText(R.string.activity_post_detail_followed);
            } else {
                mFollowTextView.setText(R.string.activity_person_follow);
            }
            mFollowTextView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.follow_post_detail_textView:
                if (isFollow()) {
                    cancelFollow();
                } else {
                    doFollow();
                }
                break;
            case R.id.like_post_detail_linearLayout:
                if (isCollect()) {
                    cancelCollect();
                } else {
                    doCollect();
                }
                break;
            case R.id.comment_post_detail_linearLayout:
                showInputDialog(this, R.string.activity_post_detail_comment);
                break;
            default:
                break;
        }
    }

    private boolean isFollow() {
        List followList = currentUser.getFollowList();
        String followId = mPost.getUserId();
        return followList.size() != 0 && followList.contains(followId);
    }

    private void doFollow() {
        if (currentUser == null) {
            return;
        }
        currentUser.addAFollow(mPost.getUserId());
        currentUser.setUsername(null);
        currentUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtil.show(PostDetailActivity.this, R.string.activity_post_detail_follow_success, Toast.LENGTH_SHORT, true);
                    mFollowTextView.setText(R.string.activity_post_detail_followed);
                    mFollowTextView.setOnClickListener(null);
                } else {
                    e.printStackTrace();
                    ToastUtil.show(PostDetailActivity.this, R.string.activity_post_detail_follow_fail, Toast.LENGTH_SHORT, false);
                }
            }
        });
    }

    private void cancelFollow() {
        if (currentUser == null) {
            return;
        }
        currentUser.reduceAFollow(mPost.getUserId());
        currentUser.setUsername(null);
        currentUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtil.show(PostDetailActivity.this, R.string.activity_post_detail_cancel_follow_success, Toast.LENGTH_SHORT, true);
                    mFollowTextView.setText(R.string.activity_person_follow);
                } else {
                    e.printStackTrace();
                    ToastUtil.show(PostDetailActivity.this, R.string.activity_post_detail_cancel_follow_fail, Toast.LENGTH_SHORT, false);
                }
            }
        });
    }

    private boolean isCollect() {
        if (currentUser == null) {
            return false;
        }
        return currentUser.getLikeList().contains(mPost.getObjectId());
    }

    private void doCollect() {
        if (currentUser == null) {
            return;
        }
        currentUser.addALikePost(mPost.getObjectId());
        currentUser.setUsername(null);
        currentUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    mPost.addALikeCount();
                    mPost.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                ToastUtil.show(PostDetailActivity.this, R.string.activity_post_detail_collect_success, Toast.LENGTH_SHORT, true);
                                mLikeImageView.setImageResource(R.drawable.ic_like_selected);
                                mLikeTextView.setText(R.string.activity_post_detail_collected);
                            } else {
                                e.printStackTrace();
                                ToastUtil.show(PostDetailActivity.this, R.string.activity_post_detail_collect_error, Toast.LENGTH_SHORT, true);
                            }
                        }
                    });
                } else {
                    e.printStackTrace();
                    ToastUtil.show(PostDetailActivity.this, R.string.activity_post_detail_collect_fail, Toast.LENGTH_SHORT, false);
                }
            }
        });
    }

    private void cancelCollect() {
        if (currentUser == null) {
            return;
        }
        currentUser.removeALikePost(mPost.getObjectId());
        currentUser.setUsername(null);
        currentUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    mPost.reduceALikeCount();
                    mPost.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                ToastUtil.show(PostDetailActivity.this, R.string.activity_post_detail_cancel_collect_success, Toast.LENGTH_SHORT, true);
                                mLikeImageView.setImageResource(R.drawable.ic_like_count);
                                mLikeTextView.setText(R.string.activity_post_detail_like);
                            } else {
                                e.printStackTrace();
                                ToastUtil.show(PostDetailActivity.this, R.string.activity_post_detail_cancel_collect_error, Toast.LENGTH_SHORT, true);
                            }
                        }
                    });
                } else {
                    e.printStackTrace();
                    ToastUtil.show(PostDetailActivity.this, R.string.activity_post_detail_cancel_collect_fail, Toast.LENGTH_SHORT, false);
                }
            }
        });
    }

    private void doComment(String comment) {
        mPost.addAComment(comment, currentUser.getObjectId(), getStringDate());
        mPost.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtil.show(PostDetailActivity.this, R.string.activity_post_detail_comment_success, Toast.LENGTH_SHORT, true);
                    // 刷新列表
                } else {
                    e.printStackTrace();
                    ToastUtil.show(PostDetailActivity.this, R.string.activity_post_detail_comment_fail, Toast.LENGTH_SHORT, false);
                }
            }
        });
    }

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
                        String result = editText.getText().toString();
                        if (!TextUtil.isEmpty(result)) {
                            doComment(result);
                        }
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

    public static String getStringDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return formatter.format(new Date());
    }

}
