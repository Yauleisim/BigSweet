package com.graduation.yau.bigsweet.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.yau.bigsweet.PostActivity;
import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.util.StartActivityUtil;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private ViewPager mHomeShiftViewPager;
    private HomeShiftViewPagerAdapter mHomeShiftViewPagerAdapter;
    private TabLayout mHomeTabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initView(root);
        initEvent(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(View root) {
        mHomeShiftViewPager = root.findViewById(R.id.follow_recommend_home_viewPager);
        mHomeTabLayout = root.findViewById(R.id.follow_recommend_home_tabLayout);
    }

    private void initEvent(View root) {
        root.findViewById(R.id.post_home_imageView).setOnClickListener(this);
        root.findViewById(R.id.me_home_imageView).setOnClickListener(this);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FollowFragment());
        fragments.add(new RecommendFragment());

        mHomeShiftViewPagerAdapter = new HomeShiftViewPagerAdapter(getChildFragmentManager(), fragments, new String[]{"关注", "推荐"});
        mHomeShiftViewPager.setAdapter(mHomeShiftViewPagerAdapter);

        mHomeTabLayout.setupWithViewPager(mHomeShiftViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_home_imageView:
                StartActivityUtil.go(getActivity(), PostActivity.class);
                break;
            case R.id.me_home_imageView:
                if (getActivity() instanceof goToPersonFragment) {
                    ((goToPersonFragment) getActivity()).go();
                }
                break;
            default:
                break;
        }
    }

    public interface goToPersonFragment {
        void go();
    }
}
