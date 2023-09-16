package com.example.myapplication.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Adapter.HangMucAdapter;
import com.example.myapplication.Adapter.LoaiHangMucAdapter;
import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.Model.ClassHangMuc;
import com.example.myapplication.Model.ClassLoaiHangMuc;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_HangMucChi_GiaoDich#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_HangMucChi_GiaoDich extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    private String mParam2;
    EditText editFind;
    private RecyclerView recyclerViewHangMucGiaoDich;
    SQLiteDatabase database;
    private HangMucAdapter HangMucAdapter;
    private List<ClassLoaiHangMuc> loaiHangMucList;
    List<ClassHangMuc> hangMucList;




    public Fragment_HangMucChi_GiaoDich() {
        // Required empty public constructor
    }

    public static Fragment_HangMucChi_GiaoDich newInstance() {
        Fragment_HangMucChi_GiaoDich fragment = new Fragment_HangMucChi_GiaoDich();
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
            //  mParam2 = getArguments().getString(ARG_PARAM2);
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

        // Tạo danh sách các mục chính (loại hạng mục) và các mục con (hạng mục)
        hangMucList = createLoaiHangMucList(); // Gán danh sách vào biến toàn cục



        // Khởi tạo adapter cho RecyclerView chính (loại hạng mục)
        HangMucAdapter = new HangMucAdapter(hangMucList);

        // Thiết lập adapter cho RecyclerView chính (loại hạng mục)
        recyclerViewHangMucGiaoDich.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewHangMucGiaoDich.setAdapter(HangMucAdapter);

        HangMucAdapter.setOnHangMucItemClickListener(new HangMucAdapter.OnHangMucItemClickListener() {
            @Override
            public void onHangMucItemClick(String tenHangMuc) {
                int idHm =  findMaHangMucByName(tenHangMuc);
                loadFragment(new FragmentGiaoDich(-1,idHm));
                Log.d( "onHangMucItemClick: ",String.valueOf(idHm));
            }
        });

        // Đặt sự kiện click cho hangMucAdapter


        addEvent();

        return  view;
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
    private List<String> findApproximateMatches(String searchTerm, List<String> dataList) {
        List<String> approximateMatches = new ArrayList<>();
        for (String data : dataList) {
            if (data.toLowerCase().contains(searchTerm.toLowerCase())) {
                approximateMatches.add(data);
            }
        }
        return approximateMatches;
    }
    private List<ClassLoaiHangMuc> createFilteredLoaiHangMucList(List<String> loaiHangMucs) {
        List<ClassLoaiHangMuc> filteredLoaiHangMucList = new ArrayList<>();

        for (ClassLoaiHangMuc loaiHangMuc : loaiHangMucList) {
            if (loaiHangMucs.contains(loaiHangMuc.getTenLoaiHangMuc())) {
                filteredLoaiHangMucList.add(loaiHangMuc);
            }
        }

        return filteredLoaiHangMucList;
    }

    private List<ClassHangMuc> createLoaiHangMucList() {
        List<ClassHangMuc> HangMucList = new ArrayList<>();

        // Tạo các mục chính (loại hạng mục) và thêm các mục con (hạng mục) tương ứng
        List<String> getAllLoaiHangMuc = getAllLoaiHangMucNames();

        for (int i = 0; i < getAllLoaiHangMuc.size(); i++) {
            String TenLoaiHM = getAllLoaiHangMuc.get(i);
            List<String> getAllHangMuc = getAllHangMucNames(TenLoaiHM);

            for (int j = 0; j < getAllHangMuc.size(); j++) {
                String TenHM = getAllHangMuc.get(j);
                HangMucList.add(new ClassHangMuc(TenHM));
            }
        }

        return HangMucList;
    }
    public List<String> getAllLoaiHangMucNames() {
        List<String> loaiHangMucNames = new ArrayList<>();

        // Câu lệnh SELECT để lấy tên các loại hạng mục
        String selectQuery = "SELECT * FROM LoaiHangMuc ";

        // Thực hiện truy vấn và lấy con trỏ (Cursor) chứa kết quả
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Di chuyển con trỏ đến vị trí đầu tiên
        if (cursor.moveToFirst()) {
            do {
                // Lấy tên loại hạng mục từ cột TenLoaiHangMuc
                @SuppressLint("Range") String tenLoaiHangMuc = cursor.getString(cursor.getColumnIndex("TenLoaiHangMuc"));
                // Thêm tên loại hạng mục vào danh sách
                loaiHangMucNames.add(tenLoaiHangMuc);
            } while (cursor.moveToNext());
        }

        // Đóng con trỏ và cơ sở dữ liệu
        cursor.close();

        return loaiHangMucNames;
    }

    public List<String> getAllHangMucNames(String tenLoaiHangMuc) {
        List<String> HangMucNames = new ArrayList<>();

        // Câu lệnh SELECT để lấy tên các loại hạng mục
        String selectQuery = "SELECT * FROM HangMuc hm, LoaiHangmuc lhm, loaigiaodich lgd WHERE lhm.tenloaihangmuc = '"+tenLoaiHangMuc+"' and lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich and hm.loaihangmuc = lhm.maloaihangmuc and lgd.maloaigiaodich = 1";

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

    private void addEvent(){
        // Set sự kiện click vào item hạng mục

//        loaiHangMucAdapter.getHangMucAdapter().setOnHangMucItemClickListener(new HangMucAdapter.OnHangMucItemClickListener() {
//            @Override
//            public void onHangMucItemClick(String tenHangMuc) {
//                // Xử lý khi item con được click
//                // Ở đây, tên hạng mục đã được lấy ra và có thể sử dụng trong việc xử lý tiếp theo
//                // Ví dụ: hiển thị Toast, mở màn hình chi tiết hạng mục, vv.
//                Toast.makeText(getActivity(), "Đã click vào hạng mục: " + tenHangMuc, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    // Trong phương thức findHangMucIDByTenHangMuc()
    private int findHangMucIDByTenHangMuc(String tenHangMuc) {
        // Khởi tạo một biến để lưu trữ mã hạng mục tìm được
        int hangMucID = -1;

        // Câu lệnh SELECT để tìm mã hạng mục dựa trên tên hạng mục
        String selectQuery = "SELECT MaHangMuc FROM HangMuc WHERE TenHangMuc = ?";
        Cursor cursor = database.rawQuery(selectQuery, new String[]{tenHangMuc});

        // Di chuyển con trỏ đến vị trí đầu tiên
        if (cursor.moveToFirst()) {
            // Lấy giá trị của cột MaHangMuc từ con trỏ và gán vào biến hangMucID
            @SuppressLint("Range") int idHangMuc = cursor.getInt(cursor.getColumnIndex("MaHangMuc"));
            hangMucID = idHangMuc; // Gán giá trị vào biến hangMucID
        }

        // Đóng con trỏ
        cursor.close();

        // Trả về mã hạng mục tìm được
        return hangMucID;
    }


    private void addControl(View view) {
        recyclerViewHangMucGiaoDich = view.findViewById(R.id.recyclerViewHangMucGiaoDich);
    }
}