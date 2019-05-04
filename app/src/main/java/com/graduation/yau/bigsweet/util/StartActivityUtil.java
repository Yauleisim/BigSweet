package com.graduation.yau.bigsweet.util;

import android.content.Context;
import android.content.Intent;

import com.graduation.yau.bigsweet.model.Order;
import com.graduation.yau.bigsweet.model.Post;
import com.graduation.yau.bigsweet.model.Product;

/**
 * Created by YAULEISIM on 2019/4/2.
 */

public class StartActivityUtil {

    public static void go(Context fromActivity, Class toActivity) {
        try {
            Intent mIntent = new Intent(fromActivity, toActivity);
            fromActivity.startActivity(mIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goWithFlag(Context fromActivity, Class toActivity, int flag) {
        try {
            Intent mIntent = new Intent(fromActivity, toActivity);
            mIntent.setFlags(flag);
            fromActivity.startActivity(mIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goWithPost(Context fromActivity, Class toActivity, Post post) {
        try {
            Intent mIntent = new Intent(fromActivity, toActivity);
            mIntent.putExtra("post", post);
            fromActivity.startActivity(mIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goWithClassification(Context fromActivity, Class toActivity, String classification) {
        try {
            Intent mIntent = new Intent(fromActivity, toActivity);
            mIntent.putExtra("classification", classification);
            fromActivity.startActivity(mIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goWithProduct(Context fromActivity, Class toActivity, Product product) {
        try {
            Intent mIntent = new Intent(fromActivity, toActivity);
            mIntent.putExtra("product", product);
            fromActivity.startActivity(mIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goWithTitle(Context fromActivity, Class toActivity, String title) {
        try {
            Intent mIntent = new Intent(fromActivity, toActivity);
            mIntent.putExtra("title", title);
            fromActivity.startActivity(mIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goWithOrder(Context fromActivity, Class toActivity, Order order) {
        try {
            Intent mIntent = new Intent(fromActivity, toActivity);
            mIntent.putExtra("order", order);
            fromActivity.startActivity(mIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
