package com.graduation.yau.bigsweet.person;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YAULEISIM on 2019/4/9.
 */

public class PersonShiftViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = null;

    private String[] mTitles;

    /**
     * Instantiates a new ab fragment pager adapter.
     *
     * @param mFragmentManager the m fragment manager
     * @param fragmentList     the fragment list
     */
    public PersonShiftViewPagerAdapter(FragmentManager mFragmentManager,
                                       ArrayList<Fragment> fragmentList) {
        super(mFragmentManager);
        mFragmentList = fragmentList;
    }

    public PersonShiftViewPagerAdapter(FragmentManager mFragmentManager,
                                       List<Fragment> fragmentList, String[] titles) {
        super(mFragmentManager);
        mFragmentList = fragmentList;
        this.mTitles = titles;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        if (position < mFragmentList.size()) {
            fragment = mFragmentList.get(position);
        } else {
            fragment = mFragmentList.get(0);
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null && mTitles.length > 0)
            return mTitles[position];
        return null;
    }
}
