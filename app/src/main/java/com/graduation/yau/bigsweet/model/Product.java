package com.graduation.yau.bigsweet.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by YAULEISIM on 2019/4/27.
 */

public class Product extends BmobObject {

    private String title;

    private int price;

    private int sale;

    private String pictureOneUrl;

    private String pictureTwoUrl;

    private String pictureThreeUrl;

    private String tagOne;

    private String tagTwo;

    private String tagThree;

    public String getTagOne() {
        return tagOne;
    }

    public void setTagOne(String tagOne) {
        this.tagOne = tagOne;
    }

    public String getTagTwo() {
        return tagTwo;
    }

    public void setTagTwo(String tagTwo) {
        this.tagTwo = tagTwo;
    }

    public String getTagThree() {
        return tagThree;
    }

    public void setTagThree(String tagThree) {
        this.tagThree = tagThree;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public String getPictureOneUrl() {
        return pictureOneUrl;
    }

    public void setPictureOneUrl(String pictureOneUrl) {
        this.pictureOneUrl = pictureOneUrl;
    }

    public String getPictureTwoUrl() {
        return pictureTwoUrl;
    }

    public void setPictureTwoUrl(String pictureTwoUrl) {
        this.pictureTwoUrl = pictureTwoUrl;
    }

    public String getPictureThreeUrl() {
        return pictureThreeUrl;
    }

    public void setPictureThreeUrl(String pictureThreeUrl) {
        this.pictureThreeUrl = pictureThreeUrl;
    }
}
