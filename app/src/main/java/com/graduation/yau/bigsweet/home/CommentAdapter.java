package com.graduation.yau.bigsweet.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.model.User;
import com.graduation.yau.bigsweet.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by YAULEISIM on 2019/5/3.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private List<String[]> mCommentList;
    private Context mContext;

    public CommentAdapter(Context context, List<String[]> data) {
        this.mCommentList = data;
        this.mContext = context;
    }

    @android.support.annotation.NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@android.support.annotation.NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post_comment, viewGroup, false);
        return new CommentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final @NonNull ViewHolder viewHolder, int i) {
        String content = mCommentList.get(i)[0];
        String time = mCommentList.get(i)[2];
        String userId = mCommentList.get(i)[1];

        viewHolder.mContentTextView.setText(content);
        viewHolder.mTimeTextView.setText(time);

        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        userBmobQuery.addWhereEqualTo("objectId", userId);
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 1) {
                        User poster = object.get(0);
                        viewHolder.mNameTextView.setText(poster.getUsername());
                        if (TextUtil.isEmpty(poster.getAvatarUrl())) {
                            viewHolder.mAvatarImageView.setBackgroundResource(R.mipmap.ic_person_avatar);
                        } else {
                            Glide.with(mContext).load(poster.getAvatarUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(viewHolder.mAvatarImageView);
                        }
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mCommentList == null) {
            return 0;
        }
        return mCommentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mAvatarImageView;
        TextView mNameTextView, mContentTextView, mTimeTextView;

        ViewHolder(View v) {
            super(v);
            mAvatarImageView = v.findViewById(R.id.avatar_item_post_comment_imageView);
            mNameTextView = v.findViewById(R.id.name_item_post_comment_textView);
            mContentTextView = v.findViewById(R.id.content_item_post_comment_textView);
            mTimeTextView = v.findViewById(R.id.time_item_post_comment_textView);
        }
    }
}
