package com.graduation.yau.bigsweet.shop;

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
import com.graduation.yau.bigsweet.PostDetailActivity;
import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.model.Product;
import com.graduation.yau.bigsweet.util.ConvertUtil;
import com.graduation.yau.bigsweet.util.StartActivityUtil;

import java.util.ArrayList;

/**
 * Created by YAULEISIM on 2019/4/27.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ArrayList<Product> mProductList;
    private Context mContext;

    public ProductAdapter(Context context, ArrayList<Product> data) {
        this.mProductList = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shop_list, viewGroup, false);
        return new ProductAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Product product = mProductList.get(i);

        viewHolder.mPriceTextView.setText(mContext.getString(R.string.activity_shop_price)
                + ConvertUtil.intToString(product.getPrice()));
        viewHolder.mSaleTextView.setText(mContext.getString(R.string.activity_shop_sale)
                + ConvertUtil.intToString(product.getSale()));
        viewHolder.mTagTextView.setText(product.getTagOne()
                + mContext.getString(R.string.activity_shop_tag)
                + product.getTagTwo()
                + mContext.getString(R.string.activity_shop_tag)
                + product.getTagThree());
        viewHolder.mTitleTextView.setText(product.getTitle());
        Glide.with(mContext).load(product.getPictureOneUrl()).into(viewHolder.mPicImageView);

        viewHolder.mRootConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityUtil.goWithProduct(mContext, ProductDetailActivity.class, product);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mProductList == null) {
            return 0;
        }
        return mProductList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTitleTextView, mPriceTextView, mSaleTextView, mTagTextView;
        ImageView mPicImageView;
        ConstraintLayout mRootConstraintLayout;

        ViewHolder(View v) {
            super(v);
            mTitleTextView = v.findViewById(R.id.title_shop_list_textView);
            mPriceTextView = v.findViewById(R.id.price_shop_list_textView);
            mSaleTextView = v.findViewById(R.id.sale_shop_list_textView);
            mPicImageView = v.findViewById(R.id.picture_shop_list_imageView);
            mTagTextView = v.findViewById(R.id.tag_shop_list_textView);
            mRootConstraintLayout = v.findViewById(R.id.root_item_shop_list_constraintLayout);
        }
    }
}
