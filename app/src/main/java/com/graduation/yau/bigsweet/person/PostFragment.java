package com.graduation.yau.bigsweet.person;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.home.RecommendFragment;
import com.graduation.yau.bigsweet.model.Post;
import com.graduation.yau.bigsweet.model.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by YAULEISIM on 2019/4/9.
 */

public class PostFragment extends RecommendFragment {

    @Override
    protected void initData() {
        BmobQuery<Post> posterQuery = new BmobQuery<>();
        User currentUser = BmobUser.getCurrentUser(User.class);
        posterQuery.addWhereEqualTo("userId", currentUser.getObjectId());
        posterQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> object, BmobException e) {
                if (e == null) {
                    mPostList.clear();
                    mPostList.addAll(object);
                    mPostRecommendAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
