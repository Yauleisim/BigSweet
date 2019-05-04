package com.graduation.yau.bigsweet.person;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.model.Order;
import com.graduation.yau.bigsweet.model.Product;
import com.graduation.yau.bigsweet.model.Seller;
import com.graduation.yau.bigsweet.model.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by YAULEISIM on 2019/5/4.
 */

public class OrderDetailActivity extends BaseActivity {

    private Order mOrder;
    private TextView mConsigneeTextView, mPhoneTextView, mAddressTextView;
    private TextView mShopNameTextView, mProductNameTextView, mProductPriceTextView, mProductNumberTextView, mOrderPriceTextView;
    private TextView mOrderIdTextView, mOrderTimeTextView, mCreateOrderPersonTextView;
    private ImageView mProductPicImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrder = (Order) getIntent().getSerializableExtra("order");
        if (mOrder == null) {
            return;
        }
        loadContentLayout(R.layout.activity_order_detail);
        setTitleName(R.string.activity_order_detail_title);
    }

    @Override
    protected void initView() {
        super.initView();
        mConsigneeTextView = findViewById(R.id.consignee_order_detail_textView);
        mPhoneTextView = findViewById(R.id.phone_order_detail_textView);
        mAddressTextView = findViewById(R.id.address_order_detail_textView);

        mShopNameTextView = findViewById(R.id.shop_name_order_detail_textView);
        mProductNameTextView = findViewById(R.id.title_order_detail_textView);
        mProductPriceTextView = findViewById(R.id.price_order_detail_textView);
        mProductNumberTextView = findViewById(R.id.num_order_detail_textView);
        mOrderPriceTextView = findViewById(R.id.sum_order_detail_textView);
        mProductPicImageView = findViewById(R.id.picture_order_detail_imageView);

        mOrderIdTextView = findViewById(R.id.id_order_detail_textView);
        mOrderTimeTextView = findViewById(R.id.time_order_detail_textView);
        mCreateOrderPersonTextView = findViewById(R.id.person_order_detail_textView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mConsigneeTextView.setText(mOrder.getConsignee());
        mPhoneTextView.setText(mOrder.getPhone());
        mAddressTextView.setText(mOrder.getAddress());

        mOrderPriceTextView.setText("合计：￥ " + mOrder.getSum());
        BmobQuery<Product> productBmobQuery = new BmobQuery<>();
        productBmobQuery.addWhereEqualTo("objectId", mOrder.getProductId());
        productBmobQuery.findObjects(new FindListener<Product>() {
            @Override
            public void done(List<Product> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 1) {
                        Product mProduct = object.get(0);
                        mProductPriceTextView.setText(getString(R.string.activity_shop_price) + mProduct.getPrice());
                        mProductNumberTextView.setText("x " + mOrder.getSum() / mProduct.getPrice());
                        mProductNameTextView.setText(mProduct.getTitle());
                        Glide.with(OrderDetailActivity.this).load(mProduct.getPictureOneUrl()).into(mProductPicImageView);
                        BmobQuery<Seller> sellerBmobQuery = new BmobQuery<>();
                        sellerBmobQuery.addWhereEqualTo("objectId", mProduct.getSellerId());
                        sellerBmobQuery.findObjects(new FindListener<Seller>() {
                            @Override
                            public void done(List<Seller> object, BmobException e) {
                                if (e == null) {
                                    if (object.size() == 1) {
                                        Seller mSeller = object.get(0);
                                        mShopNameTextView.setText(mSeller.getName());
                                    }
                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

        mOrderIdTextView.setText(mOrder.getObjectId());
        mOrderTimeTextView.setText(mOrder.getCreatedAt());
        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        userBmobQuery.addWhereEqualTo("objectId", mOrder.getUserId());
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    User user = list.get(0);
                    mCreateOrderPersonTextView.setText(user.getUsername());
                } else {
                    e.printStackTrace();
                }
            }
        });

    }
}
