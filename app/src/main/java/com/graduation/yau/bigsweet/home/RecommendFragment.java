package com.graduation.yau.bigsweet.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.model.Post;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by YAULEISIM on 2019/4/10.
 */

public class RecommendFragment extends Fragment {

    private RecyclerView mPostRecommendRecyclerView;
    protected PostAdapter mPostRecommendAdapter;

    protected ArrayList<Post> mPostList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recommend, container, false);
        initView(root);
        initEvent();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected void initView(View root) {
        mPostRecommendRecyclerView = root.findViewById(R.id.post_recommend_recyclerView);

    }

    protected void initEvent() {
        initData();
        mPostRecommendAdapter = new PostAdapter(getContext(), mPostList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mPostRecommendRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mPostRecommendRecyclerView.setAdapter(mPostRecommendAdapter);
    }

    protected void initData() {
        BmobQuery<Post> orderQuery = new BmobQuery<>();
        orderQuery.order("-likeCount");
        BmobQuery<Post> publicQuery = new BmobQuery<>();
        publicQuery.addWhereEqualTo("isPublic", true);
        List<BmobQuery<Post>> andQuerys = new ArrayList<BmobQuery<Post>>();
        andQuerys.add(orderQuery);
        andQuerys.add(publicQuery);
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
