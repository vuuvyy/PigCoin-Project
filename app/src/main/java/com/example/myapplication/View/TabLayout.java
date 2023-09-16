package com.example.myapplication.View;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.myapplication.Adapter.PagerAdapter_ThuChi_ThongKe;
import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabLayout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabLayout extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String mParam1;

    private ViewPager viewPager;
    private com.google.android.material.tabs.TabLayout tabLayout;
    private Toolbar toolbar;
    FrameLayout frameTab;
String user;
    int thang,MaLoaiGiaoDich;
    public TabLayout() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabLayout newInstance(int param1,String user, int MaLoaiGiaoDich) {
        TabLayout fragment = new TabLayout();
        Bundle args = new Bundle();
        args.putInt("thang", param1);
        args.putString("user", String.valueOf(user));
        args.putString("MaLoaiGiaoDich", String.valueOf(MaLoaiGiaoDich));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {    //ncc
            thang = getArguments().getInt("thang"); // Use getInt instead of getString
            user = getArguments().getString("user"); // Use getInt instead of getString
            MaLoaiGiaoDich = getArguments().getInt("MaLoaiGiaoDich"); // Use getInt instead of getString

        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        AddControl(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            thang = bundle.getInt("thang"); // Retrieve the integer value using getInt
            user = bundle.getString("user"); // Retrieve the integer value using getInt
            MaLoaiGiaoDich = bundle.getInt("MaLoaiGiaoDich"); // Retrieve the integer value using getInt
        }

        // Create the adapter for the ViewPager
        PagerAdapter_ThuChi_ThongKe pagerAdapter = new PagerAdapter_ThuChi_ThongKe(getChildFragmentManager(),thang,user,MaLoaiGiaoDich);
        viewPager.setAdapter(pagerAdapter);

        // Link the TabLayout with the ViewPager
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }
    public void showTabLayout() {
        tabLayout.setVisibility(View.VISIBLE);
    }

    // Phương thức ẩn TabLayout và TabItem
    public void hideTabLayout() {
        tabLayout.setVisibility(View.GONE);
    }
    private void AddControl(View view){
        toolbar = view.findViewById(R.id.toolbar);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        frameTab= view.findViewById(R.id.frameTab);
    }

}