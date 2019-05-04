package com.graduation.yau.bigsweet.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.login.LoginActivity;
import com.graduation.yau.bigsweet.model.Certification;
import com.graduation.yau.bigsweet.model.Seller;
import com.graduation.yau.bigsweet.model.User;
import com.graduation.yau.bigsweet.util.DialogUtil;
import com.graduation.yau.bigsweet.util.StartActivityUtil;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.util.TextUtil;
import com.graduation.yau.bigsweet.util.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by YAULEISIM on 2019/3/28.
 */

public class SettingsActivity extends BaseActivity {

    private ConstraintLayout mCertifyConstraintLayout;
    private TextView mCertifyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_settings);
        setTitleName(R.string.activity_settings_title);
    }

    @Override
    protected void initView() {
        super.initView();
        mCertifyConstraintLayout = findViewById(R.id.certification_settings_constraintLayout);
        mCertifyTextView = findViewById(R.id.certification_settings_textView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        findViewById(R.id.accountAndSecurity_settings_constraintLayout).setOnClickListener(this);
        findViewById(R.id.user_message_settings_constraintLayout).setOnClickListener(this);
        findViewById(R.id.about_settings_constraintLayout).setOnClickListener(this);
        findViewById(R.id.sign_out_settings_constraintLayout).setOnClickListener(this);
        findViewById(R.id.feedback_settings_constraintLayout).setOnClickListener(this);

        isCertify();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.user_message_settings_constraintLayout:
                StartActivityUtil.go(SettingsActivity.this, UserMessageActivity.class);
                break;
            case R.id.accountAndSecurity_settings_constraintLayout:
                StartActivityUtil.go(SettingsActivity.this, AccountSecurityActivity.class);
                break;
            case R.id.certification_settings_constraintLayout:
                StartActivityUtil.go(SettingsActivity.this, CertifyActivity.class);
                break;
            case R.id.about_settings_constraintLayout:
                StartActivityUtil.go(SettingsActivity.this, AboutActivity.class);
                break;
            case R.id.feedback_settings_constraintLayout:
                StartActivityUtil.go(SettingsActivity.this, FeedbackActivity.class);
                break;
            case R.id.sign_out_settings_constraintLayout:
                DialogUtil.showNormalDialog(SettingsActivity.this, R.string.dialog_quit_title, R.string.dialog_quit_content,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BmobUser.logOut();
                                ToastUtil.show(SettingsActivity.this, R.string.dialog_quit_toast, Toast.LENGTH_SHORT, true);
                                StartActivityUtil.goWithFlag(SettingsActivity.this, LoginActivity.class,
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                finish();
                            }
                        });
                break;
            default:
                break;
        }
    }

    private void isCertify() {
        BmobQuery<Seller> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("userId", BmobUser.getCurrentUser(User.class).getObjectId());
        bmobQuery.findObjects(new FindListener<Seller>() {
            @Override
            public void done(List<Seller> object, BmobException e) {
                if (e == null) {
                    if (object != null && object.size() > 0 && object.get(0).isAuthentication()) {
                        mCertifyTextView.setText(R.string.activity_settings_certification_success);
                    } else {
                        mCertifyTextView.setText(R.string.activity_settings_certification);
                        mCertifyConstraintLayout.setOnClickListener(SettingsActivity.this);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
