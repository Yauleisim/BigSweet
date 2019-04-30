package com.graduation.yau.bigsweet.shop;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.model.Product;
import com.graduation.yau.bigsweet.model.Seller;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by YAULEISIM on 2019/4/30.
 */

public class EditOrderActivity extends BaseActivity {

    private Product mProduct;
    private Seller mSeller;
    private EditText mConsigneeEditText, mPhoneEditText, mAddressEditText, mWordsEditText;
    private TextView mSubmitTextView, mPriceSumTextView, mShopNameTextView, mTitleTextView, mSumTextView, mPriceTextView;
    private ImageView mPicImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProduct = (Product) getIntent().getSerializableExtra("product");
        if (mProduct == null) {
            return;
        }
        loadContentLayout(R.layout.activity_edit_order);
        setTitleName(R.string.activity_edit_order_title);
    }

    @Override
    protected void initView() {
        super.initView();
        mConsigneeEditText = findViewById(R.id.consignee_edit_order_editText);
        mPhoneEditText = findViewById(R.id.phone_edit_order_editText);
        mAddressEditText = findViewById(R.id.address_edit_order_editText);
        mWordsEditText = findViewById(R.id.words_edit_order_editText);

        mSubmitTextView = findViewById(R.id.submit_edit_order_textView);
        mPriceSumTextView = findViewById(R.id.price_sum_edit_order_textView);

        mShopNameTextView = findViewById(R.id.shop_name_edit_order_textView);
        mPicImageView = findViewById(R.id.picture_edit_order_imageView);
        mTitleTextView = findViewById(R.id.title_edit_order_textView);
        mPriceTextView = findViewById(R.id.price_edit_order_textView);
        mSumTextView = findViewById(R.id.sum_edit_order_textView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mTitleTextView.setText(mProduct.getTitle());
        mPriceTextView.setText(getString(R.string.activity_shop_price) + mProduct.getPrice());
        Glide.with(EditOrderActivity.this).load(mProduct.getPictureOneUrl()).into(mPicImageView);

        mPriceSumTextView.setText(getString(R.string.activity_shop_price) + mProduct.getPrice());

        BmobQuery<Seller> sellerBmobQuery = new BmobQuery<>();
        sellerBmobQuery.addWhereEqualTo("objectId", mProduct.getSellerId());
        sellerBmobQuery.findObjects(new FindListener<Seller>() {
            @Override
            public void done(List<Seller> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 1) {
                        mSeller = object.get(0);
                        mShopNameTextView.setText(mSeller.getName());
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
