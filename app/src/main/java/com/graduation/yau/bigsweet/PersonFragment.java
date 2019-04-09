package com.graduation.yau.bigsweet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.yau.bigsweet.settings.SettingsActivity;
import com.graduation.yau.bigsweet.settings.UserMessageActivity;
import com.graduation.yau.bigsweet.util.StartActivityUtil;

public class PersonFragment extends Fragment implements View.OnClickListener {

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

    private void initView(View root) {
    }

    private void initEvent(View root) {
        root.findViewById(R.id.settings_person_imageView).setOnClickListener(this);
        root.findViewById(R.id.post_person_imageView).setOnClickListener(this);
        root.findViewById(R.id.edit_person_textView).setOnClickListener(this);
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
