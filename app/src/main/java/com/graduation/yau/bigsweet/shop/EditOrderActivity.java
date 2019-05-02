package com.graduation.yau.bigsweet.shop;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.model.Order;
import com.graduation.yau.bigsweet.model.Product;
import com.graduation.yau.bigsweet.model.Seller;
import com.graduation.yau.bigsweet.util.ConvertUtil;
import com.graduation.yau.bigsweet.util.TextUtil;
import com.graduation.yau.bigsweet.util.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by YAULEISIM on 2019/4/30.
 */

public class EditOrderActivity extends BaseActivity {

    private Product mProduct;
    private Seller mSeller;
    private EditText mConsigneeEditText, mPhoneEditText, mAddressEditText, mWordsEditText;
    private TextView mSubmitTextView, mPriceSumTextView, mShopNameTextView, mTitleTextView, mSumTextView, mPriceTextView, mAddTextView, mReduceTextView;
    private ImageView mPicImageView;
    private int sum = 1;

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

        mAddTextView = findViewById(R.id.add_edit_order_textView);
        mReduceTextView = findViewById(R.id.reduce_edit_order_textView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAddressEditText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        mAddressEditText.setSingleLine(false);
        mAddressEditText.setHorizontallyScrolling(false);

        mTitleTextView.setText(mProduct.getTitle());
        mPriceTextView.setText(getString(R.string.activity_shop_price) + mProduct.getPrice());
        mSumTextView.setText(ConvertUtil.intToString(sum));
        Glide.with(EditOrderActivity.this).load(mProduct.getPictureOneUrl()).into(mPicImageView);

        mPriceSumTextView.setText(getString(R.string.activity_shop_price) + mProduct.getPrice() * sum);

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

        mAddTextView.setOnClickListener(this);
        mReduceTextView.setOnClickListener(this);
        mSubmitTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.add_edit_order_textView:
                mSumTextView.setText(ConvertUtil.intToString(++sum));
                mPriceSumTextView.setText(getString(R.string.activity_shop_price) + mProduct.getPrice() * sum);
                break;
            case R.id.reduce_edit_order_textView:
                if (sum == 1) {
                    ToastUtil.show(this, R.string.activity_edit_order_error, Toast.LENGTH_SHORT, false);
                } else {
                    mSumTextView.setText(ConvertUtil.intToString(--sum));
                    mPriceSumTextView.setText(getString(R.string.activity_shop_price) + mProduct.getPrice() * sum);
                }
                break;
            case R.id.submit_edit_order_textView:
                doSubmit();
                break;
            default:
                break;
        }
    }

    private void doSubmit() {
        String consignee = mConsigneeEditText.getText().toString();
        String phone = mPhoneEditText.getText().toString();
        String address = mAddressEditText.getText().toString();
        if (TextUtil.isEmpty(consignee) || TextUtil.isEmpty(phone) || TextUtil.isEmpty(address)) {
            ToastUtil.show(this, R.string.activity_edit_order_null, Toast.LENGTH_SHORT, false);
            return;
        }

        Order order = new Order();
        order.setAddress(address);
        order.setConsignee(consignee);
        order.setPhone(phone);
        order.setProductId(mProduct.getObjectId());
        String words = mWordsEditText.getText().toString();
        if (!TextUtil.isEmpty(words)) {
            order.setWords(words);
        }
        order.setSum(ConvertUtil.stringToInt(mSumTextView.getText().toString()));
        order.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastUtil.show(EditOrderActivity.this, R.string.activity_edit_order_success, Toast.LENGTH_SHORT, true);
                    // todo 可能要写一个订单完成的页面
                    finish();
                } else {
                    e.printStackTrace();
                    ToastUtil.show(EditOrderActivity.this, R.string.activity_edit_order_fail, Toast.LENGTH_SHORT, false);
                }
            }
        });
    }
}
