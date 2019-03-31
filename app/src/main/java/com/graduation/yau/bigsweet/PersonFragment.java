package com.graduation.yau.bigsweet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PersonFragment extends Fragment implements View.OnClickListener {

    private TextView mUserNameTextView, mSignatureTextView, mSignOutStateTextView, mSignInTextView;

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
        mUserNameTextView = root.findViewById(R.id.user_name_person_textView);
        mSignatureTextView = root.findViewById(R.id.signature_person_textView);
        mSignOutStateTextView = root.findViewById(R.id.sign_out_state_person_textView);
        mSignInTextView = root.findViewById(R.id.sign_in_person_textView);
    }

    private void initEvent(View root) {
        root.findViewById(R.id.like_person_constraintLayout).setOnClickListener(this);
        root.findViewById(R.id.post_person_constraintLayout).setOnClickListener(this);
        root.findViewById(R.id.settings_person_constraintLayout).setOnClickListener(this);

        // 检查登录状态
        if (false) {
            // 登录
            mUserNameTextView.setVisibility(View.VISIBLE);
            mSignatureTextView.setVisibility(View.VISIBLE);
            mSignOutStateTextView.setVisibility(View.GONE);
            mSignInTextView.setVisibility(View.GONE);
        } else {
            // 未登录
            mSignOutStateTextView.setVisibility(View.VISIBLE);
            mSignInTextView.setVisibility(View.VISIBLE);
            mUserNameTextView.setVisibility(View.GONE);
            mSignatureTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.like_person_constraintLayout:
                break;
            case R.id.post_person_constraintLayout:
                break;
            case R.id.settings_person_constraintLayout:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.sign_in_person_textView:
                break;
            default:
                break;
        }
    }
}
