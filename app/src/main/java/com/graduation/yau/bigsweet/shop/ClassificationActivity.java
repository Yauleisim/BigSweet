package com.graduation.yau.bigsweet.shop;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.model.Product;
import com.graduation.yau.bigsweet.util.ClassifyUtil;
import com.graduation.yau.bigsweet.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by YAULEISIM on 2019/5/2.
 */

public class ClassificationActivity extends BaseActivity {

    private RecyclerView mProductRecyclerView;
    private ProductAdapter mProductAdapter;
    private ArrayList<Product> mProductList = new ArrayList<>();
    private String mClassification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra("classification");
        if (TextUtil.isEmpty(title)) {
            title = getString(R.string.activity_classification_title);
            mClassification = ClassifyUtil.CLASSIFICATION_OTHER;
        } else {
            mClassification = ClassifyUtil.getClassification(title);
        }
        loadContentLayout(R.layout.activity_classification);
        setTitleName(title);
    }

    @Override
    protected void initView() {
        super.initView();
        mProductRecyclerView = findViewById(R.id.product_classification_recyclerView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        initData();
        mProductAdapter = new ProductAdapter(this, mProductList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mProductRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mProductRecyclerView.setAdapter(mProductAdapter);
    }

    private void initData() {
        BmobQuery<Product> productQuery = new BmobQuery<>();
        productQuery.addWhereEqualTo("classification", mClassification);
        productQuery.findObjects(new FindListener<Product>() {
            @Override
            public void done(List<Product> object, BmobException e) {
                if (e == null) {
                    mProductList.clear();
                    mProductList.addAll(object);
                    mProductAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
