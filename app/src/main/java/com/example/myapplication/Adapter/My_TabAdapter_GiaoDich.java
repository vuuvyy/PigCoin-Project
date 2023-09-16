package com.example.myapplication.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.View.Fragment_HangMucChi_GiaoDich;
import com.example.myapplication.View.Fragment_HangMucThu_GiaoDich;

public class My_TabAdapter_GiaoDich extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 2;


    public My_TabAdapter_GiaoDich(FragmentManager fragmentManager, String user) {
        super(fragmentManager);
    }
    public My_TabAdapter_GiaoDich(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return  Fragment_HangMucChi_GiaoDich.newInstance();
            case 1:
                return  Fragment_HangMucThu_GiaoDich.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Chi";
            case 1:
                return "Thu";
            default:
                return "";
        }
    }
}
