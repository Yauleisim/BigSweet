package com.graduation.yau.bigsweet.shop;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.graduation.yau.bigsweet.GlideImageLoader;
import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.model.Product;
import com.graduation.yau.bigsweet.model.Seller;
import com.graduation.yau.bigsweet.util.StartActivityUtil;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by YAULEISIM on 2019/4/30.
 */

public class ProductDetailActivity extends BaseActivity {

    private Product mProduct;
    private TextView mPriceTextView, mTitleTextView, mDescribeTextView, mSellerTextView, mProductSumTextView, mBuyTextView, mAddCarTextView, mShareTextView;
    private Banner mPicBanner;
    private Seller mSeller;
    private ImageView mSellerAvatarImageView;
    private ConstraintLayout mGoToShopConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProduct = (Product) getIntent().getSerializableExtra("product");
        if (mProduct == null) {
            return;
        }
        loadContentLayout(R.layout.activity_product_detail);
        setTitleName(R.string.activity_product_detail_title);
    }

    @Override
    protected void initView() {
        super.initView();
        mPriceTextView = findViewById(R.id.price_product_detail_textView);
        mTitleTextView = findViewById(R.id.title_product_detail_textView);
        mDescribeTextView = findViewById(R.id.describe_product_detail_textView);
        mSellerTextView = findViewById(R.id.seller_product_detail_textView);
        mProductSumTextView = findViewById(R.id.product_sum_product_detail_textView);
        mPicBanner = findViewById(R.id.banner_product_detail_banner);
        mSellerAvatarImageView = findViewById(R.id.avatar_product_detail_imageView);
        mBuyTextView = findViewById(R.id.buy_product_detail_textView);
        mAddCarTextView = findViewById(R.id.add_car_product_detail_textView);
        mShareTextView = findViewById(R.id.share_product_detail_textView);
        mGoToShopConstraintLayout = findViewById(R.id.go_to_shop_product_detail_constraintLayout);
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        //设置图片加载器
        mPicBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<String> imageList = new ArrayList<>();
        imageList.add(mProduct.getPictureOneUrl());
        imageList.add(mProduct.getPictureTwoUrl());
        imageList.add(mProduct.getPictureThreeUrl());
        mPicBanner.setImages(imageList);
        //banner设置方法全部调用完毕时最后调用
        mPicBanner.start();

        mPriceTextView.setText(getString(R.string.activity_shop_price) + mProduct.getPrice());
        mTitleTextView.setText(mProduct.getTitle());
        mDescribeTextView.setText(mProduct.getDescribe());

        BmobQuery<Seller> sellerBmobQuery = new BmobQuery<>();
        sellerBmobQuery.addWhereEqualTo("objectId", mProduct.getSellerId());
        sellerBmobQuery.findObjects(new FindListener<Seller>() {
            @Override
            public void done(List<Seller> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 1) {
                        mSeller = object.get(0);
                        mSellerTextView.setText(mSeller.getName());
                        Glide.with(ProductDetailActivity.this).load(mSeller.getAvatarUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(mSellerAvatarImageView);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

        BmobQuery<Product> productBmobQuery = new BmobQuery<>();
        productBmobQuery.addWhereEqualTo("sellerId", mProduct.getSellerId());
        productBmobQuery.findObjects(new FindListener<Product>() {
            @Override
            public void done(List<Product> object, BmobException e) {
                if (e == null) {
                    mProductSumTextView.setText(getString(R.string.activity_product_detail_sale)
                            + object.size() + getString(R.string.activity_product_detail_sum));
                } else {
                    e.printStackTrace();
                }
            }
        });

        mBuyTextView.setOnClickListener(this);
        mAddCarTextView.setOnClickListener(this);
        mShareTextView.setOnClickListener(this);
        mGoToShopConstraintLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.buy_product_detail_textView:
                StartActivityUtil.goWithProduct(ProductDetailActivity.this, EditOrderActivity.class, mProduct);
                break;
            case R.id.add_car_product_detail_textView:
                break;
            case R.id.share_product_detail_textView:
                break;
            case R.id.go_to_shop_product_detail_constraintLayout:
                break;
            default:
                break;
        }
    }
}
