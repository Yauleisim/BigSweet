package com.graduation.yau.bigsweet;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.home.PostAdapter;
import com.graduation.yau.bigsweet.model.Post;
import com.graduation.yau.bigsweet.model.Product;
import com.graduation.yau.bigsweet.shop.ProductAdapter;
import com.graduation.yau.bigsweet.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by YAULEISIM on 2019/5/5.
 */

public class SearchActivity extends BaseActivity {

    private ImageView mBackImageView, mSearchImageView, mClearImageView;
    private EditText mSearchEditText;
    private String fromWhere;
    public static final String FROM_SHOP = "shop";
    public static final String FROM_HOME = "home";

    private RecyclerView mDataRecyclerView;
    private PostAdapter mPostAdapter;
    private ProductAdapter mProductAdapter;
    private ArrayList<Post> mPostList = new ArrayList<>();
    private ArrayList<Product> mProductList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fromWhere = getIntent().getStringExtra("from");
        if (fromWhere == null) {
            return;
        }
        loadContentLayout(R.layout.activity_search);
        hideTitle();
    }

    @Override
    protected void initView() {
        super.initView();
        mBackImageView = findViewById(R.id.back_search_imageView);
        mSearchImageView = findViewById(R.id.go_search_imageView);
        mSearchEditText = findViewById(R.id.search_search_editText);
        mDataRecyclerView = findViewById(R.id.data_search_recyclerView);
        mClearImageView = findViewById(R.id.clear_search_imageView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBackImageView.setOnClickListener(this);
        mSearchImageView.setOnClickListener(this);
        mClearImageView.setOnClickListener(this);

        if (fromWhere.equals(FROM_HOME)) {
            mPostAdapter = new PostAdapter(this, mPostList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mDataRecyclerView.setLayoutManager(layoutManager);
            layoutManager.setOrientation(OrientationHelper.VERTICAL);
            mDataRecyclerView.setAdapter(mPostAdapter);
        } else if (fromWhere.equals(FROM_SHOP)) {
            mProductAdapter = new ProductAdapter(this, mProductList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mDataRecyclerView.setLayoutManager(layoutManager);
            layoutManager.setOrientation(OrientationHelper.VERTICAL);
            mDataRecyclerView.setAdapter(mProductAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.back_search_imageView:
                finish();
                break;
            case R.id.go_search_imageView:
                doSearch();
                break;
            case R.id.clear_search_imageView:
                mSearchEditText.setText("");
            default:
                break;
        }
    }

    private void doSearch() {
        if (fromWhere.equals(FROM_HOME)) {
            searchPostData();
        } else if (fromWhere.equals(FROM_SHOP)) {
            searchProductData();
        }
    }

    private void searchPostData() {
        final String content = mSearchEditText.getText().toString();
        if (TextUtil.isEmpty(content)) {
            return;
        }

        BmobQuery<Post> postBmobQuery = new BmobQuery<>();
        postBmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                mPostList.clear();
                for (Post post : list) {
                    String postContent = post.getContent();
                    String postTopic = post.getTopic();
                    if (!TextUtil.isEmpty(postContent) && postContent.contains(content)) {
                        mPostList.add(post);
                    } else if (!TextUtil.isEmpty(postTopic) && postTopic.contains(content)) {
                        mPostList.add(post);
                    }
                }
                mPostAdapter.notifyDataSetChanged();
            }
        });

    }

    private void searchProductData() {
        final String content = mSearchEditText.getText().toString();
        if (TextUtil.isEmpty(content)) {
            return;
        }

        BmobQuery<Product> postBmobQuery = new BmobQuery<>();
        postBmobQuery.findObjects(new FindListener<Product>() {
            @Override
            public void done(List<Product> list, BmobException e) {
                mProductList.clear();
                for (Product product : list) {
                    String title = product.getTitle();
                    String tag1 = product.getTagOne();
                    String tag2 = product.getTagTwo();
                    String tag3 = product.getTagThree();
                    if (!TextUtil.isEmpty(title) && title.contains(content)) {
                        mProductList.add(product);
                    } else if (!TextUtil.isEmpty(tag1) && tag1.contains(content)) {
                        mProductList.add(product);
                    } else if (!TextUtil.isEmpty(tag2) && tag2.contains(content)) {
                        mProductList.add(product);
                    } else if (!TextUtil.isEmpty(tag3) && tag3.contains(content)) {
                        mProductList.add(product);
                    }
                }
                mProductAdapter.notifyDataSetChanged();
            }
        });

    }

}
