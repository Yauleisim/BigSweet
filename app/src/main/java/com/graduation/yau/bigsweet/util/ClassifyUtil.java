package com.graduation.yau.bigsweet.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YAULEISIM on 2019/5/2.
 */

public class ClassifyUtil {
    public static final String CLASSIFICATION_BAKE = "烘培";
    public static final String CLASSIFICATION_SUGAR = "糖水";
    public static final String CLASSIFICATION_FRUIT = "水果";
    public static final String CLASSIFICATION_DRINK = "饮品";
    public static final String CLASSIFICATION_SNACK = "零食";
    public static final String CLASSIFICATION_SEASONING = "调料";
    public static final String CLASSIFICATION_BOX = "礼盒";
    public static final String CLASSIFICATION_UTENSILS = "器皿";
    public static final String CLASSIFICATION_OTHER = "其他";
    private static List<String> mClassificationList = new ArrayList<>();

    public static String getClassification(String classification) {
        initList();
        if (mClassificationList.contains(classification)) {
            return classification;
        } else {
            return CLASSIFICATION_OTHER;
        }
    }

    private static void initList() {
        mClassificationList.clear();
        mClassificationList.add(CLASSIFICATION_BAKE);
        mClassificationList.add(CLASSIFICATION_SUGAR);
        mClassificationList.add(CLASSIFICATION_FRUIT);
        mClassificationList.add(CLASSIFICATION_DRINK);
        mClassificationList.add(CLASSIFICATION_SNACK);
        mClassificationList.add(CLASSIFICATION_SEASONING);
        mClassificationList.add(CLASSIFICATION_BOX);
        mClassificationList.add(CLASSIFICATION_UTENSILS);
    }
}
