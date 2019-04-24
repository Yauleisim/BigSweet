package com.graduation.yau.bigsweet.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.graduation.yau.bigsweet.PostDetailActivity;
import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.model.Post;
import com.graduation.yau.bigsweet.model.User;
import com.graduation.yau.bigsweet.util.ConvertUtil;
import com.graduation.yau.bigsweet.util.StartActivityUtil;
import com.graduation.yau.bigsweet.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by YAULEISIM on 2019/4/24.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> mPostList;
    private Context mContext;

    public PostAdapter(Context context, ArrayList<Post> data) {
        this.mPostList = data;
        this.mContext = context;
    }

    @android.support.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@android.support.annotation.NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_list, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        Post currentPost = mPostList.get(position);

        viewHolder.mLikeCountTextView.setText(ConvertUtil.intToString(currentPost.getLikeCount()));
        viewHolder.mShareCountTextView.setText(ConvertUtil.intToString(currentPost.getShareCount()));
        viewHolder.mCommentCountTextView.setText(ConvertUtil.intToString(currentPost.getCommentCount()));

        viewHolder.mContentTextView.setText(currentPost.getContent());
        String label = currentPost.getTopic();
        if (TextUtil.isEmpty(label)) {
            viewHolder.mLabelConstraintLayout.setVisibility(View.GONE);
        } else {
            viewHolder.mLabelTextView.setText(label);
        }

        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        userBmobQuery.addWhereEqualTo("objectId", currentPost.getUserId());
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 1) {
                        User poster = object.get(0);
                        viewHolder.mNameTextView.setText(poster.getUsername());
                        Glide.with(mContext).load(poster.getAvatarUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(viewHolder.mAvatarImageView);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

        String picOneUrl = currentPost.getPictureOne();
        if (!TextUtil.isEmpty(picOneUrl)) {
            Glide.with(mContext).load(picOneUrl).into(viewHolder.mOnePictureImageView);
        } else {
            viewHolder.mOnePictureImageView.setVisibility(View.INVISIBLE);
        }
        String picTwoUrl = currentPost.getPictureTwo();
        if (!TextUtil.isEmpty(picTwoUrl)) {
            Glide.with(mContext).load(picTwoUrl).into(viewHolder.mTwoPictureImageView);
        } else {
            viewHolder.mTwoPictureImageView.setVisibility(View.INVISIBLE);
        }
        String picThreeUrl = currentPost.getPictureThree();
        if (!TextUtil.isEmpty(picThreeUrl)) {
            Glide.with(mContext).load(picThreeUrl).into(viewHolder.mThreePictureImageView);
        } else {
            viewHolder.mThreePictureImageView.setVisibility(View.INVISIBLE);
        }
        if (TextUtil.isEmpty(picThreeUrl) && TextUtil.isEmpty(picTwoUrl) && TextUtil.isEmpty(picOneUrl)) {
            viewHolder.mPictureLinearLayout.setVisibility(View.GONE);
        }

        viewHolder.mRootConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityUtil.go(mContext, PostDetailActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mPostList == null) {
            return 0;
        }
        return mPostList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mLikeCountTextView, mCommentCountTextView, mShareCountTextView;
        TextView mLabelTextView, mContentTextView;
        ImageView mOnePictureImageView, mTwoPictureImageView, mThreePictureImageView;
        ImageView mAvatarImageView;
        TextView mNameTextView;
        LinearLayout mPictureLinearLayout;
        ConstraintLayout mLabelConstraintLayout, mRootConstraintLayout;

        ViewHolder(View v) {
            super(v);
            mLikeCountTextView = v.findViewById(R.id.like_count_item_home_list_textView);
            mCommentCountTextView = v.findViewById(R.id.comment_count_item_home_list_textView);
            mShareCountTextView = v.findViewById(R.id.share_count_item_home_list_textView);
            mLabelTextView = v.findViewById(R.id.label_item_home_list_textView);
            mContentTextView = v.findViewById(R.id.content_item_home_list_textView);
            mOnePictureImageView = v.findViewById(R.id.one_picture_item_home_list_ImageView);
            mTwoPictureImageView = v.findViewById(R.id.two_picture_item_home_list_ImageView);
            mThreePictureImageView = v.findViewById(R.id.three_picture_item_home_list_ImageView);
            mAvatarImageView = v.findViewById(R.id.avatar_item_home_list_imageView);
            mNameTextView = v.findViewById(R.id.name_item_home_list_textView);
            mPictureLinearLayout = v.findViewById(R.id.picture_item_home_list_linearLayout);
            mLabelConstraintLayout = v.findViewById(R.id.label_item_home_list_constraintLayout);
            mRootConstraintLayout = v.findViewById(R.id.root_item_home_list_constraintLayout);
        }
    }

}
