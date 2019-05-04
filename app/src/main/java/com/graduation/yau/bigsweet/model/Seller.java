package com.graduation.yau.bigsweet.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by YAULEISIM on 2019/4/30.
 */

public class Seller extends BmobObject {

    private String name;

    private String avatarUrl;

    private boolean authentication;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }
}
