package com.graduation.yau.bigsweet.home;

import com.graduation.yau.bigsweet.model.Post;
import com.graduation.yau.bigsweet.model.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by YAULEISIM on 2019/4/10.
 */

public class FollowFragment extends RecommendFragment {

    @Override
    protected void initData() {
        BmobQuery<Post> publicQuery = new BmobQuery<>();
        publicQuery.addWhereEqualTo("isPublic", true);
        BmobQuery<Post> followQuery = new BmobQuery<>();
        User currentUser = BmobUser.getCurrentUser(User.class);
        followQuery.addWhereContainedIn("userId", currentUser.getFollowList());

        List<BmobQuery<Post>> andQuerys = new ArrayList<BmobQuery<Post>>();
        andQuerys.add(publicQuery);
        andQuerys.add(followQuery);
        BmobQuery<Post> postBmobQuery = new BmobQuery<>();
        postBmobQuery.and(andQuerys);
        postBmobQuery.findObjects(new FindListener<Post>() {
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
