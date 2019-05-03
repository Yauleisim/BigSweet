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
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

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
        BmobQuery<Post> postBmobQuery = new BmobQuery<>();
        String bql = "select * from Post where isPublic = ? order by -likeCount";
        postBmobQuery.setSQL(bql);
        postBmobQuery.setPreparedParams(new Boolean[]{true});

        postBmobQuery.doSQLQuery(new SQLQueryListener<Post>() {
            @Override
            public void done(BmobQueryResult<Post> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<Post> object = (List<Post>) bmobQueryResult.getResults();
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
