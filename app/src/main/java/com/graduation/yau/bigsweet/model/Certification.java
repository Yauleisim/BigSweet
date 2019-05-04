package com.graduation.yau.bigsweet.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by YAULEISIM on 2019/5/4.
 */

public class Certification extends BmobObject {

    private String name;

    private String number;

    private String type;

    private String picture;

    private String userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
