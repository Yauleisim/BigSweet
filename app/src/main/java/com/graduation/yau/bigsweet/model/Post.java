package com.graduation.yau.bigsweet.model;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by YAULEISIM on 2019/4/23.
 */

public class Post extends BmobObject {

    private static final long serialVersionUID = 4530180474274266107L;
    private String pictureOne;

    private String pictureTwo;

    private String pictureThree;

    private String userId;

    private String content;

    private String topic;

    private boolean isPublic;

    private int likeCount;

    private int shareCount;

    private int commentCount;

    private List<String[]> commentList = new ArrayList<>();

    public List<String[]> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<String[]> commentList) {
        this.commentList = commentList;
    }

    public void addAComment(String comment, String userId, String time) {
        commentList.add(new String[]{comment, userId, time});
        commentCount++;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void addALikeCount() {
        likeCount++;
    }

    public void reduceALikeCount() {
        likeCount--;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getPictureOne() {
        return pictureOne;
    }

    public void setPictureOne(String pictureOne) {
        this.pictureOne = pictureOne;
    }

    public String getPictureTwo() {
        return pictureTwo;
    }

    public void setPictureTwo(String pictureTwo) {
        this.pictureTwo = pictureTwo;
    }

    public String getPictureThree() {
        return pictureThree;
    }

    public void setPictureThree(String pictureThree) {
        this.pictureThree = pictureThree;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
