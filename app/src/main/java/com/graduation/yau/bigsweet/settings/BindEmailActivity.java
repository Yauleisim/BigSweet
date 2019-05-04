package com.graduation.yau.bigsweet.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.graduation.yau.bigsweet.R;
import com.graduation.yau.bigsweet.model.User;
import com.graduation.yau.bigsweet.base.BaseActivity;
import com.graduation.yau.bigsweet.util.TextUtil;
import com.graduation.yau.bigsweet.util.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by YAULEISIM on 2019/4/17.
 */

public class BindEmailActivity extends BaseActivity {

    private EditText mEmailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentLayout(R.layout.activity_bind_email);
        setTitleName(R.string.activity_bind_email_title);
    }

    @Override
    protected void initView() {
        super.initView();
        mEmailEditText = findViewById(R.id.email_bind_email_editText);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        findViewById(R.id.bind_bind_email_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bind_bind_email_button:
                doBind();
                break;
            default:
                break;
        }
    }

    private void doBind() {
        final String email = mEmailEditText.getText().toString();
        if (TextUtil.isEmpty(email)) {
            ToastUtil.show(BindEmailActivity.this, R.string.activity_bind_email_no_email, Toast.LENGTH_SHORT, false);
            return;
        }
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("email", email);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (object != null && object.size() > 0 && object.get(0).getEmailVerified()) {
                    ToastUtil.show(BindEmailActivity.this, R.string.activity_bind_email_exist_email, Toast.LENGTH_SHORT, false);
                } else {
                    User user = BmobUser.getCurrentUser(User.class);
                    user.setEmail(email);
                    user.setUsername(null);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                BmobUser.requestEmailVerify(email, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            ToastUtil.show(BindEmailActivity.this, R.string.activity_bind_email_success, Toast.LENGTH_SHORT, true);
                                            finish();
                                        } else {
                                            e.printStackTrace();
                                            ToastUtil.show(BindEmailActivity.this, R.string.activity_bind_email_fail, Toast.LENGTH_SHORT, false);
                                        }
                                    }
                                });
                            } else {
                                e.printStackTrace();
                                ToastUtil.show(BindEmailActivity.this, R.string.activity_bind_email_fail, Toast.LENGTH_SHORT, false);
                            }
                        }
                    });
                }
            }
        });
    }
}
