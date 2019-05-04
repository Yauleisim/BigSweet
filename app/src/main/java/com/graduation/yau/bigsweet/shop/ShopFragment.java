package com.graduation.yau.bigsweet.shop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.graduation.yau.bigsweet.GlideImageLoader;
import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.home.HomeFragment;
import com.graduation.yau.bigsweet.model.Product;
import com.graduation.yau.bigsweet.util.ClassifyUtil;
import com.graduation.yau.bigsweet.util.StartActivityUtil;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ShopFragment extends Fragment implements View.OnClickListener {

    private Banner mShopBanner;
    private RecyclerView mProductRecyclerView;
    private ProductAdapter mProductAdapter;
    private ArrayList<Product> mProductList = new ArrayList<>();
    private ConstraintLayout mBakeConstraintLayout, mSugarConstraintLayout, mFruitConstraintLayout, mDrinkConstraintLayout, mSnackConstraintLayout, mSeasoningConstraintLayout, mBoxConstraintLayout, mUtensilsConstraintLayout;
    private ImageView mReleaseImageView, mMeImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initEvent();
    }

    private void initView(View root) {
        mShopBanner = (Banner) root.findViewById(R.id.banner_shop_banner);
        mProductRecyclerView = root.findViewById(R.id.product_shop_recyclerView);
        mBakeConstraintLayout = root.findViewById(R.id.bake_shop_constraintLayout);
        mSugarConstraintLayout = root.findViewById(R.id.sugar_shop_constraintLayout);
        mFruitConstraintLayout = root.findViewById(R.id.fruit_shop_constraintLayout);
        mDrinkConstraintLayout = root.findViewById(R.id.drink_shop_constraintLayout);
        mSeasoningConstraintLayout = root.findViewById(R.id.seasoning_shop_constraintLayout);
        mSnackConstraintLayout = root.findViewById(R.id.snack_shop_constraintLayout);
        mBoxConstraintLayout = root.findViewById(R.id.box_shop_constraintLayout);
        mUtensilsConstraintLayout = root.findViewById(R.id.utensils_shop_constraintLayout);
        mReleaseImageView = root.findViewById(R.id.release_shop_imageView);
        mMeImageView = root.findViewById(R.id.me_shop_imageView);
    }

    private void initEvent() {
        //设置图片加载器
        mShopBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<String> imageList = new ArrayList<>();
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555491983&di=f24c766e66d4669ef881a53de9a9c40e&imgtype=jpg&er=1&src=http%3A%2F%2Fimg1.gtimg.com%2Fhenan%2Fpics%2Fhv1%2F236%2F9%2F2054%2F133563881.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555492116&di=0537032891ac16c7e16bf2082dee613a&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.futurelnt.com%2FPublic%2FKindeditor%2Fattached%2Fimage%2F20150319%2F20150319080707_37106.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554897448821&di=27818c0f5609ac6c395a1da465b73b80&imgtype=0&src=http%3A%2F%2Fimg02.tooopen.com%2Fimages%2F20150821%2Ftooopen_sy_139453151134.jpg");
        mShopBanner.setImages(imageList);
        //banner设置方法全部调用完毕时最后调用
        mShopBanner.start();

        initData();
        mProductAdapter = new ProductAdapter(getContext(), mProductList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mProductRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mProductRecyclerView.setAdapter(mProductAdapter);

        mBakeConstraintLayout.setOnClickListener(this);
        mSugarConstraintLayout.setOnClickListener(this);
        mFruitConstraintLayout.setOnClickListener(this);
        mDrinkConstraintLayout.setOnClickListener(this);
        mSeasoningConstraintLayout.setOnClickListener(this);
        mSnackConstraintLayout.setOnClickListener(this);
        mBoxConstraintLayout.setOnClickListener(this);
        mUtensilsConstraintLayout.setOnClickListener(this);

        mReleaseImageView.setOnClickListener(this);
        mMeImageView.setOnClickListener(this);
    }

    private void initData() {
        BmobQuery<Product> productQuery = new BmobQuery<>();
        productQuery.order("-sale");
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
        switch (v.getId()) {
            case R.id.bake_shop_constraintLayout:
                StartActivityUtil.goWithClassification(getActivity(), ClassificationActivity.class, ClassifyUtil.CLASSIFICATION_BAKE);
                break;
            case R.id.sugar_shop_constraintLayout:
                StartActivityUtil.goWithClassification(getActivity(), ClassificationActivity.class, ClassifyUtil.CLASSIFICATION_SUGAR);
                break;
            case R.id.fruit_shop_constraintLayout:
                StartActivityUtil.goWithClassification(getActivity(), ClassificationActivity.class, ClassifyUtil.CLASSIFICATION_FRUIT);
                break;
            case R.id.drink_shop_constraintLayout:
                StartActivityUtil.goWithClassification(getActivity(), ClassificationActivity.class, ClassifyUtil.CLASSIFICATION_DRINK);
                break;
            case R.id.seasoning_shop_constraintLayout:
                StartActivityUtil.goWithClassification(getActivity(), ClassificationActivity.class, ClassifyUtil.CLASSIFICATION_SEASONING);
                break;
            case R.id.snack_shop_constraintLayout:
                StartActivityUtil.goWithClassification(getActivity(), ClassificationActivity.class, ClassifyUtil.CLASSIFICATION_SNACK);
                break;
            case R.id.box_shop_constraintLayout:
                StartActivityUtil.goWithClassification(getActivity(), ClassificationActivity.class, ClassifyUtil.CLASSIFICATION_BOX);
                break;
            case R.id.utensils_shop_constraintLayout:
                StartActivityUtil.goWithClassification(getActivity(), ClassificationActivity.class, ClassifyUtil.CLASSIFICATION_UTENSILS);
                break;
            case R.id.me_shop_imageView:
                // 个人页
                if (getActivity() instanceof HomeFragment.goToPersonFragment) {
                    ((HomeFragment.goToPersonFragment) getActivity()).go();
                }
                break;
            case R.id.release_shop_imageView:
                // 发布商品页
                break;
            default:
                break;
        }
    }
}
