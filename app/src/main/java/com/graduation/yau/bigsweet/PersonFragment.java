package com.graduation.yau.bigsweet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PersonFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_person, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(View root) {
        root.findViewById(R.id.like_person_constraintLayout).setOnClickListener(this);
        root.findViewById(R.id.post_person_constraintLayout).setOnClickListener(this);
        root.findViewById(R.id.settings_person_constraintLayout).setOnClickListener(this);
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
            default:
                break;
        }
    }
}
