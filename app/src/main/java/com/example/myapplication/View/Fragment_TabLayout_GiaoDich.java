package com.example.myapplication.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.myapplication.Adapter.HangMucAdapter;
import com.example.myapplication.Adapter.My_TabAdapter_GiaoDich;
import com.example.myapplication.R;


public class Fragment_TabLayout_GiaoDich extends Fragment implements HangMucAdapter.OnHangMucItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private ViewPager viewPager_GiaoDich;
    private com.google.android.material.tabs.TabLayout tabLayout_GiaoDich;

    FrameLayout frametab_GiaoDich;

    int thang;
    String user;
    public Fragment_TabLayout_GiaoDich() {
        // Required empty public constructor
    }

    public static Fragment_TabLayout_GiaoDich newInstance(String user) {
        Fragment_TabLayout_GiaoDich fragment = new Fragment_TabLayout_GiaoDich();
        Bundle args = new Bundle();
        args.putString("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getString("user"); // Use getInt instead of getString
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__tab_layout__giao_dich, container, false);
        AddControl(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            thang = bundle.getInt("thang"); // Retrieve the integer value using getInt
            user = getArguments().getString("user"); // Use getInt instead of getString
        }

        // Create the adapter for the ViewPager
        My_TabAdapter_GiaoDich pagerAdapter = new My_TabAdapter_GiaoDich(getChildFragmentManager(),user);
        viewPager_GiaoDich.setAdapter(pagerAdapter);

        // Link the TabLayout with the ViewPager
        tabLayout_GiaoDich.setupWithViewPager(viewPager_GiaoDich);


        return view;    }
    public void showTabLayout() {
        tabLayout_GiaoDich.setVisibility(View.VISIBLE);
    }

    // Phương thức ẩn TabLayout và TabItem
    public void hideTabLayout() {
        tabLayout_GiaoDich.setVisibility(View.GONE);
    }
    private void AddControl(View view){
        viewPager_GiaoDich = view.findViewById(R.id.viewPager_GiaoDich);
        tabLayout_GiaoDich = view.findViewById(R.id.tabLayout_GiaoDich);
        frametab_GiaoDich= view.findViewById(R.id.frametab_GiaoDich);
    }

    @Override
    public void onHangMucItemClick(String tenHangMuc) {
        FragmentGiaoDich fragmentGiaoDich = new FragmentGiaoDich();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frametab_GiaoDich, fragmentGiaoDich)
                .addToBackStack(null)
                .commit();
    }
}