package com.graduation.yau.bigsweet.person;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.model.Order;
import com.graduation.yau.bigsweet.model.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by YAULEISIM on 2019/5/2.
 */

public class OrderFragment extends Fragment {

    private RecyclerView mOrderRecyclerView;
    protected OrderAdapter mOrderAdapter;

    protected ArrayList<Order> mOrderList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        initView(root);
        initEvent();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(View root) {
        mOrderRecyclerView = root.findViewById(R.id.order_order_recyclerView);
    }

    private void initEvent() {
        initData();
        mOrderAdapter = new OrderAdapter(getContext(), mOrderList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mOrderRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mOrderRecyclerView.setAdapter(mOrderAdapter);
    }

    private void initData() {
        BmobQuery<Order> orderQuery = new BmobQuery<>();
        orderQuery.addWhereEqualTo("userId", BmobUser.getCurrentUser(User.class).getObjectId());
        orderQuery.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> object, BmobException e) {
                if (e == null) {
                    mOrderList.clear();
                    mOrderList.addAll(object);
                    mOrderAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
