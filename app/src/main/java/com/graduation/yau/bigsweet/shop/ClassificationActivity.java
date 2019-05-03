package com.graduation.yau.bigsweet.shop;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.model.Product;
import com.graduation.yau.bigsweet.util.ClassifyUtil;
import com.graduation.yau.bigsweet.util.DialogUtil;
import com.graduation.yau.bigsweet.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by YAULEISIM on 2019/5/2.
 */

public class ClassificationActivity extends BaseActivity {

    private RecyclerView mProductRecyclerView;
    private ProductAdapter mProductAdapter;
    private ArrayList<Product> mProductList = new ArrayList<>();
    private String mClassification;
    private LinearLayout mSaleOrderLinearLayout, mPriceOrderLinearLayout;
    private TextView mSaleOrderTextView, mPriceOrderTextView;
    private ImageView mSaleOrderImageView, mPriceOrderImageView;
    private String[] items = {"升序", "降序"};
    private int mOrderChoice = 0;
    private boolean isSale;

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
        mSaleOrderLinearLayout = findViewById(R.id.sale_classification_linearLayout);
        mPriceOrderLinearLayout = findViewById(R.id.price_classification_linearLayout);
        mPriceOrderImageView = findViewById(R.id.price_classification_imageView);
        mSaleOrderImageView = findViewById(R.id.sale_classification_imageView);
        mSaleOrderTextView = findViewById(R.id.sale_classification_textView);
        mPriceOrderTextView = findViewById(R.id.price_classification_textView);
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

        mSaleOrderLinearLayout.setOnClickListener(this);
        mPriceOrderLinearLayout.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sale_classification_linearLayout:
                isSale = true;
                DialogUtil.showSingleChoiceDialog(this, items, R.string.activity_classification_dialog_title, choiceListener, positiveListener);
                break;
            case R.id.price_classification_linearLayout:
                isSale = false;
                DialogUtil.showSingleChoiceDialog(this, items, R.string.activity_classification_dialog_title, choiceListener, positiveListener);
                break;
            default:
                break;
        }
    }

    private DialogInterface.OnClickListener choiceListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mOrderChoice = which;
        }
    };

    private DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            updateData();
        }
    };

    private void updateData() {
        BmobQuery<Product> productQuery = new BmobQuery<>();
        String bql;
        if (mOrderChoice == 0) {
            // 升序
            if (isSale) {
                // 销量
                bql = "select * from Product where classification = ? order by sale";
            } else {
                // 价格
                bql = "select * from Product where classification = ? order by price";
            }
        } else {
            // 降序
            if (isSale) {
                // 销量
                bql = "select * from Product where classification = ? order by -sale";
            } else {
                // 价格
                bql = "select * from Product where classification = ? order by -price";
            }
        }

        productQuery.setSQL(bql);
        productQuery.setPreparedParams(new String[]{mClassification});
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

    private void updateUI() {
        if (mOrderChoice == 0) {
            // 升序
            if (isSale) {
                // 销量
                mSaleOrderTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
                mSaleOrderImageView.setImageResource(R.drawable.ic_order_asc);
                mPriceOrderTextView.setTextColor(getResources().getColor(R.color.default_text_color));
                mPriceOrderImageView.setImageResource(R.drawable.ic_order_no);
            } else {
                // 价格
                mPriceOrderTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
                mPriceOrderImageView.setImageResource(R.drawable.ic_order_asc);
                mSaleOrderTextView.setTextColor(getResources().getColor(R.color.default_text_color));
                mSaleOrderImageView.setImageResource(R.drawable.ic_order_no);
            }
        } else {
            // 降序
            if (isSale) {
                // 销量
                mSaleOrderTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
                mSaleOrderImageView.setImageResource(R.drawable.ic_order_desc);
                mPriceOrderTextView.setTextColor(getResources().getColor(R.color.default_text_color));
                mPriceOrderImageView.setImageResource(R.drawable.ic_order_no);
            } else {
                // 价格
                mPriceOrderTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
                mPriceOrderImageView.setImageResource(R.drawable.ic_order_desc);
                mSaleOrderTextView.setTextColor(getResources().getColor(R.color.default_text_color));
                mSaleOrderImageView.setImageResource(R.drawable.ic_order_no);
            }
        }
    }
}
