package com.graduation.yau.bigsweet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    private Banner mShopBanner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEvent(view);
    }

    private void initEvent(View root) {
        mShopBanner = (Banner) root.findViewById(R.id.banner_shop_banner);
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
    }
}
