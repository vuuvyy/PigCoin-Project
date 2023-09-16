package com.example.myapplication.Adapter;

// Other import statements and class declaration follows...

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.View.ChiFragmentChiTietThongKe;
import com.example.myapplication.View.ThuFragmentChiTietThongKe;

public class PagerAdapter_ThuChi_ThongKe extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 2;
    private int thang,MaLoaiGiaoDich;
    String user;

    public PagerAdapter_ThuChi_ThongKe(FragmentManager fragmentManager, int thang, String user, int MaLoaiGiaoDich) {
        super(fragmentManager);
        this.thang = thang;
        this.user = user;
        this.MaLoaiGiaoDich = MaLoaiGiaoDich;
    }
    public PagerAdapter_ThuChi_ThongKe(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return  ChiFragmentChiTietThongKe.newInstance(thang, user, 1);
            case 1:
                return  ThuFragmentChiTietThongKe.newInstance(thang, user, 2);
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
