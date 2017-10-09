package com.tuespotsolutions.ravinder077.ggsdav;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                taba tab22 = new taba();
                return tab22;

            case 1:
                NoticeBoard tab2 = new NoticeBoard();
                return tab2;
            case 2:
                NoticeBoardActivity cff=new NoticeBoardActivity();
                return cff;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}