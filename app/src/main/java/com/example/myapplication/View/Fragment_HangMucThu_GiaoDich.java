package com.example.myapplication.View;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapter.HangMucAdapter;
import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.Model.ClassHangMuc;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_HangMucThu_GiaoDich#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_HangMucThu_GiaoDich extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerViewHangMucGiaoDich;
    SQLiteDatabase database;
    private HangMucAdapter hangMucAdapter;

    public Fragment_HangMucThu_GiaoDich() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment Fragment_HangMucChi_GiaoDich.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_HangMucThu_GiaoDich newInstance() {
        Fragment_HangMucThu_GiaoDich fragment = new Fragment_HangMucThu_GiaoDich();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__hang_muc_chi__giao_dich, container, false);
        addControl(view);
        // Tạo và mở cơ sở dữ liệu
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
//        databaseHelper.deleteDatabase(getActivity());
        database = databaseHelper.getWritableDatabase();

        // Tạo danh sách các hạng mục thu
        List<ClassHangMuc> bien = createLoaiHangMucList();

        // Khởi tạo adapter cho RecyclerView chính (hạng mục thu)
        hangMucAdapter = new HangMucAdapter(bien);
        // Thiết lập adapter cho RecyclerView chính (loại hạng mục)

        recyclerViewHangMucGiaoDich.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewHangMucGiaoDich.setAdapter(hangMucAdapter);
        hangMucAdapter.setOnHangMucItemClickListener(new HangMucAdapter.OnHangMucItemClickListener() {
            @Override
            public void onHangMucItemClick(String tenHangMuc) {
                int idHm =  findMaHangMucByName(tenHangMuc);
                loadFragment(new FragmentGiaoDich(-1,idHm));
                Log.d( "onHangMucItemClick: ",String.valueOf(idHm));

            }
        });
        //Log.d("TAG",bien.toString());

        return  view;
    }
    protected void loadFragment(Fragment fragment) {
        // Sử dụng getChildFragmentManager() để lấy đối tượng FragmentManager từ Fragment
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (getParentFragment() instanceof Fragment_TabLayout_GiaoDich) {
            ((Fragment_TabLayout_GiaoDich) getParentFragment()).hideTabLayout();
        }
        fragmentTransaction.replace(R.id.fre, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @SuppressLint("Range")
    public int findMaHangMucByName(String name) {
        int maHM = -1;

        // Câu lệnh SELECT để lấy mã ví từ tên người dùng
        String selectQuery = "SELECT hm.MaHangMuc FROM HangMuc hm WHERE hm.TenHangMuc = '"+name+"'";

        // Thực hiện truy vấn và lấy con trỏ (Cursor) chứa kết quả
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Di chuyển con trỏ đến vị trí đầu tiên
        if (cursor.moveToFirst()) {
            // Lấy mã ví từ cột MaVi
            maHM = cursor.getInt(cursor.getColumnIndex("MaHangMuc"));
        }

        // Đóng con trỏ
        cursor.close();

        return maHM;
    }
    private List<ClassHangMuc> createLoaiHangMucList() {
        List<ClassHangMuc> HangMucList = new ArrayList<>();
        List<String> allHangMuc = getAllHangMucNames();

        for (int i = 0; i < allHangMuc.size(); i++) {
            String tenHangMuc = allHangMuc.get(i);
            ClassHangMuc hangMuc = new ClassHangMuc(tenHangMuc);
            //Log.d("hèdhfj", tenHangMuc);
            HangMucList.add(hangMuc);
        }

        return HangMucList;
    }

    public List<String> getAllHangMucNames() {
        List<String> HangMucNames = new ArrayList<>();

        // Câu lệnh SELECT để lấy tên các loại hạng mục
        String selectQuery = "SELECT * FROM HangMuc hm, LoaiHangmuc lhm, loaigiaodich lgd WHERE  lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich and hm.loaihangmuc = lhm.maloaihangmuc and lgd.maloaigiaodich = 2";

        // Thực hiện truy vấn và lấy con trỏ (Cursor) chứa kết quả
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Di chuyển con trỏ đến vị trí đầu tiên
        if (cursor.moveToFirst()) {
            do {
                // Lấy tên loại hạng mục từ cột TenLoaiHangMuc
                @SuppressLint("Range") String tenHangMuc = cursor.getString(cursor.getColumnIndex("TenHangMuc"));
                // Thêm tên loại hạng mục vào danh sách
                HangMucNames.add(tenHangMuc);
            } while (cursor.moveToNext());
        }

        // Đóng con trỏ và cơ sở dữ liệu
        cursor.close();

        return HangMucNames;
    }


    private void addControl(View view) {
        recyclerViewHangMucGiaoDich = view.findViewById(R.id.recyclerViewHangMucGiaoDich);

    }
}