package com.graduation.yau.bigsweet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.graduation.yau.bigsweet.settings.SettingsActivity;
import com.graduation.yau.bigsweet.settings.UserMessageActivity;
import com.graduation.yau.bigsweet.util.StartActivityUtil;
import com.graduation.yau.bigsweet.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class PersonFragment extends Fragment implements View.OnClickListener {

    private ViewPager mPersonShiftViewPager;
    private PersonShiftViewPagerAdapter mPersonShiftViewPagerAdapter;
    private TabLayout mPersonTabLayout;
    private TextView mNameTextView, mSignatureTextView, mFollowTextView, mFansTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_person, container, false);
        initView(root);
        initEvent(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        User currentUser = BmobUser.getCurrentUser(User.class);
        if (currentUser == null) {
            return;
        }
        mNameTextView.setText(currentUser.getUsername());
        String signature = currentUser.getSignature();
        if (TextUtil.isEmpty(signature)) {
            mSignatureTextView.setText(R.string.activity_person_default_signature);
        } else {
            mSignatureTextView.setText(signature);
        }
        mFollowTextView.setText(String.valueOf(currentUser.getFollowCount()));
        mFansTextView.setText(String.valueOf(currentUser.getFansCount()));
    }

    private void initView(View root) {
        mPersonShiftViewPager = root.findViewById(R.id.note_like_person_viewPager);
        mPersonTabLayout = root.findViewById(R.id.note_like_person_tabLayout);
        mNameTextView = root.findViewById(R.id.name_person_textView);
        mSignatureTextView = root.findViewById(R.id.signature_person_textView);
        mFollowTextView = root.findViewById(R.id.follow_count_person_textView);
        mFansTextView = root.findViewById(R.id.fans_count_person_textView);
    }

    private void initEvent(View root) {
        root.findViewById(R.id.settings_person_imageView).setOnClickListener(this);
        root.findViewById(R.id.post_person_imageView).setOnClickListener(this);
        root.findViewById(R.id.edit_person_textView).setOnClickListener(this);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new PostFragment());
        fragments.add(new CollectionFragment());

        mPersonShiftViewPagerAdapter = new PersonShiftViewPagerAdapter(getChildFragmentManager(), fragments, new String[]{"帖子", "收藏"});
        mPersonShiftViewPager.setAdapter(mPersonShiftViewPagerAdapter);

        mPersonTabLayout.setupWithViewPager(mPersonShiftViewPager);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_person_imageView:
                StartActivityUtil.go(getActivity(), SettingsActivity.class);
                break;
            case R.id.post_person_imageView:
                StartActivityUtil.go(getActivity(), PostActivity.class);
                break;
            case R.id.edit_person_textView:
                StartActivityUtil.go(getActivity(), UserMessageActivity.class);
                break;
            default:
                break;
        }
    }
}
