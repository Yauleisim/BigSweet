package com.graduation.yau.bigsweet.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by YAULEISIM on 2019/4/11.
 */

public class User extends BmobUser {

    private String avatarUrl;

    private String gender;

    private String signature;

    private int followCount;

    private int fansCount;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
