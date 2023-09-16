package com.example.myapplication.View;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentChiChiTietDoanhMuc extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "TenLoaiHangMuc";
    SQLiteDatabase database;
    List<String> listTenHangMuc = new ArrayList<>();

    ArrayList<com.example.myapplication.Model.ClassItemChiTietItem> ClassItemChiTietItem;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int thang;
    String TenLoaiHangMuc;
    String user;
//    RecyclerView recyclerViewTungHangMuc;

    public FragmentChiChiTietDoanhMuc() {
        // Required empty public constructor
    }


    public static FragmentChiChiTietDoanhMuc newInstance(int param1, String TenLoaiHangMuc ) {
        FragmentChiChiTietDoanhMuc fragment = new FragmentChiChiTietDoanhMuc();
        Bundle args = new Bundle();
        args.putString("thang", String.valueOf(param1));
        args.putString("TenLoaiHangMuc", String.valueOf(TenLoaiHangMuc));

        fragment.setArguments(args);
        return fragment;
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        // Get the parent activity and find the TabLayout by its ID
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        if (activity != null) {
//            TabLayout tabLayout = activity.findViewById(R.id.tabLayout);
//
//            // Hide the TabLayout
//            if (tabLayout != null) {
//                tabLayout.setVisibility(View.GONE);
//            }
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // Get the parent activity and find the TabLayout by its ID
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        if (activity != null) {
//            TabLayout tabLayout = activity.findViewById(R.id.tabLayout);
//
//            // Show the TabLayout (if needed) when the fragment is stopped
//            if (tabLayout != null) {
//                tabLayout.setVisibility(View.VISIBLE);
//            }
//        }
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private void addControl(View view){
//        recyclerViewTungHangMuc = view.findViewById(R.id.recyclerViewTungHangMuc);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
//        databaseHelper.deleteDatabase(getActivity());
        database = databaseHelper.getWritableDatabase();
        View view = inflater.inflate(R.layout.fragment_chi_chi_tiet_doanh_muc, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {

            TenLoaiHangMuc = bundle.getString("TenLoaiHangMuc");
            thang = Integer.parseInt(bundle.getString("thang"));
            thang+=1;
        }
        getTenLoaiHangMucChi(thang,user,TenLoaiHangMuc);
        TextView f = view.findViewById(R.id.f);
        f.setText(listTenHangMuc.toString());
//       addControl(view);
//RecyclerView recyclerViewTungHangMuc = view.findViewById(R.id.recyclerViewTungHangMuc);



        return view;
    }
    private void LoadChiTietDoanhMuc(){


//        ClassItemChiTietItem = new ArrayList<>();
//        BigDecimal soTien = BigDecimal.ZERO;
//
//
//       TenHangMuc = tenLoaiHangMucList.toArray(new String[0]);
//        for (int i = 0; i<tenLoaiHangMucList.size(); i++){
//
//            soTien= getSoTienTungHanMucThu(tenLoaiHangMucArray[i],thang, user);
//            ItemCategory = tenLoaiHangMucArray[i].toString();
//
//
//            TongSoTien = getTongSoTienThu(thang,user);
//            percent = soTien.divide(TongSoTien, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
//            Percentage = percent.floatValue();
//            ClassItemChiTietItem.add(new ClassItemChiTietItem(soTien,Percentage,ItemCategory,R.drawable.hamburger));
//
//        }
//
//        adapter = new CustomAdapterChiTietHangMuc(getActivity(), ClassItemChiTietItem);
//
//        // Cấu hình RecyclerView
//        reChiTietHangMucThu.setLayoutManager(new LinearLayoutManager(getActivity()));
//        reChiTietHangMucThu.setAdapter(adapter);
    }
    private void getTenLoaiHangMucChi(int thang, String user, String TenLoaiHangMuc) {

        String month = (thang < 10) ? "0" + thang : String.valueOf(thang);
        String query = "select hm.TenHangMuc from GiaoDich gd, HangMuc hm, LoaiHangMuc lhm, LoaiGiaoDich lgd , Account ac, vi vi " +
                "where lhm.TenLoaiHangMuc='"+TenLoaiHangMuc+"' and ac.UserName = '"+user+"' and strftime('%m', ngaygiaodich) = '"+month+"'" +
                "and lgd.MaLoaiGiaoDich =1 and gd.ViGiaoDich = vi.MaVi and vi.AccountID = ac.AccountID and gd.MaHangMuc = hm.MaHangMuc and hm.LoaiHangMuc = lhm.MaLoaiHangMuc and lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String tenHangMuc = cursor.getString(cursor.getColumnIndex("TenHangMuc"));
                listTenHangMuc.add(tenHangMuc);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}