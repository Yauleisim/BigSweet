package com.graduation.yau.bigsweet.person;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.model.Order;
import com.graduation.yau.bigsweet.model.Product;
import com.graduation.yau.bigsweet.model.Seller;
import com.graduation.yau.bigsweet.util.ConvertUtil;
import com.graduation.yau.bigsweet.util.StartActivityUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by YAULEISIM on 2019/5/2.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private ArrayList<Order> mOrderList;
    private Context mContext;
    private Product mProduct;
    private Seller mSeller;

    public OrderAdapter(Context context, ArrayList<Order> data) {
        this.mOrderList = data;
        this.mContext = context;
    }

    @android.support.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@android.support.annotation.NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_list, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final Order currentOrder = mOrderList.get(position);
        if (currentOrder == null) {
            return;
        }
        viewHolder.mOrderSumPriceTextView.setText("合计：￥ " + currentOrder.getSum());

        BmobQuery<Product> productBmobQuery = new BmobQuery<>();
        productBmobQuery.addWhereEqualTo("objectId", currentOrder.getProductId());
        productBmobQuery.findObjects(new FindListener<Product>() {
            @Override
            public void done(List<Product> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 1) {
                        mProduct = object.get(0);
                        viewHolder.mProductPriceTextView.setText(mContext.getString(R.string.activity_shop_price) + mProduct.getPrice());
                        viewHolder.mProductNumberTextView.setText("x " + currentOrder.getSum() / mProduct.getPrice());
                        viewHolder.mProductTitleTextView.setText(mProduct.getTitle());
                        Glide.with(mContext).load(mProduct.getPictureOneUrl()).into(viewHolder.mProductPicImageView);
                        BmobQuery<Seller> sellerBmobQuery = new BmobQuery<>();
                        sellerBmobQuery.addWhereEqualTo("objectId", mProduct.getSellerId());
                        sellerBmobQuery.findObjects(new FindListener<Seller>() {
                            @Override
                            public void done(List<Seller> object, BmobException e) {
                                if (e == null) {
                                    if (object.size() == 1) {
                                        mSeller = object.get(0);
                                        viewHolder.mShopNameTextView.setText(mSeller.getName());
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

        viewHolder.mRootConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityUtil.goWithOrder(mContext, OrderDetailActivity.class, currentOrder);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mOrderList == null) {
            return 0;
        }
        return mOrderList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mShopNameTextView, mProductTitleTextView, mProductPriceTextView, mProductNumberTextView, mOrderSumPriceTextView;
        ImageView mProductPicImageView;
        ConstraintLayout mRootConstraintLayout;

        ViewHolder(View v) {
            super(v);
            mShopNameTextView = v.findViewById(R.id.shop_name_order_list_textView);
            mProductTitleTextView = v.findViewById(R.id.title_order_list_textView);
            mProductNumberTextView = v.findViewById(R.id.num_order_list_textView);
            mProductPriceTextView = v.findViewById(R.id.price_order_list_textView);
            mProductPicImageView = v.findViewById(R.id.picture_order_list_imageView);
            mOrderSumPriceTextView = v.findViewById(R.id.sum_order_list_textView);
            mRootConstraintLayout = v.findViewById(R.id.root_item_order_list_constraintLayout);
        }
    }
}
