package com.graduation.yau.bigsweet.model;

import java.util.ArrayList;
import java.util.List;

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

    private List<String> followList = new ArrayList<>();

    private List<String> fansList = new ArrayList<>();

    public List<String> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<String> likeList) {
        this.likeList = likeList;
    }

    public void addALikePost(String postId) {
        likeList.add(postId);
    }

    public void removeALikePost(String postId) {
        likeList.remove(postId);
    }

    private List<String> likeList = new ArrayList<>();

    public List<String> getFollowList() {
        return followList;
    }

    public void setFollowList(List<String> followList) {
        this.followList = followList;
    }

    public void addAFollow(String followUserId) {
        followList.add(followUserId);
        this.followCount++;
    }

    public List<String> getFansList() {
        return fansList;
    }

    public void setFansList(List<String> fansList) {
        this.fansList = fansList;
    }

    public void addAFans(String fansUserId) {
        fansList.add(fansUserId);
        fansCount++;
    }

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
