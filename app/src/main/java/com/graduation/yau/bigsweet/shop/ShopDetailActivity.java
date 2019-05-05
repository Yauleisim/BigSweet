package com.graduation.yau.bigsweet.shop;

import android.os.Bundle;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.model.Product;
import com.graduation.yau.bigsweet.model.Seller;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by YAULEISIM on 2019/5/5.
 */

public class ShopDetailActivity extends ClassificationActivity {

    private Seller mSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSeller = (Seller) getIntent().getSerializableExtra("seller");
        if (mSeller == null) {
            return;
        }
        super.onCreate(savedInstanceState);
        setTitleName(mSeller.getName());
    }

    @Override
    protected void initData() {
        BmobQuery<Product> productQuery = new BmobQuery<>();
        productQuery.addWhereEqualTo("sellerId", mSeller.getObjectId());
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

    @Override
    protected void updateData() {
        BmobQuery<Product> productQuery = new BmobQuery<>();
        String bql;
        if (mOrderChoice == 0) {
            // 升序
            if (isSale) {
                // 销量
                bql = "select * from Product where sellerId = ? order by sale";
            } else {
                // 价格
                bql = "select * from Product where sellerId = ? order by price";
            }
        } else {
            // 降序
            if (isSale) {
                // 销量
                bql = "select * from Product where sellerId = ? order by -sale";
            } else {
                // 价格
                bql = "select * from Product where sellerId = ? order by -price";
            }
        }

        productQuery.setSQL(bql);
        productQuery.setPreparedParams(new String[]{mSeller.getObjectId()});
        productQuery.doSQLQuery(new SQLQueryListener<Product>() {
            @Override
            public void done(BmobQueryResult<Product> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<Product> object = (List<Product>) bmobQueryResult.getResults();
                    if (mProductList != null) {
                        int previousSize = mProductList.size();
                        mProductList.clear();
                        mProductAdapter.notifyItemRangeRemoved(0, previousSize);
                        mProductList.addAll(object);
                        mProductAdapter.notifyItemRangeInserted(0, object.size());
                    }
                    updateUI();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
