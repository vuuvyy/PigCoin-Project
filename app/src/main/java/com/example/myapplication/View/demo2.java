package com.example.myapplication.View;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapter.CustomAdapter_Show_HangMucGiaoDich;
import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.Model.ClassGiaoDich;
import com.example.myapplication.Model.ClassHangMuc;
import com.example.myapplication.R;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class demo2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2, mParam3;
    SQLiteDatabase database;
    List<String> listTenHangMuc = new ArrayList<>();

    ArrayList<com.example.myapplication.Model.ClassItemChiTietItem> ClassItemChiTietItem;

    int thang;
    int MaLoaiGiaoDich;
    String TenLoaiDanhMuc;
    String user;
    RecyclerView recyclerViewHangMucGiaoDichShowThongKe;
    CustomAdapter_Show_HangMucGiaoDich setAdapter;
    List<ClassGiaoDich> loaiHangMucList = new ArrayList<>();


    public demo2() {
    }
    public static demo2 newInstance(int param1, String TenLoaiDanhMuc, int maLoaiGiaoDich, String user) {
        demo2 fragment = new demo2();
        Bundle args = new Bundle();
        args.putString("thang", String.valueOf(param1));
        args.putString("maLoaiGiaoDich", String.valueOf(maLoaiGiaoDich));
        args.putString("TenLoaiDoanhMuc", String.valueOf(TenLoaiDanhMuc));
        args.putString("user", String.valueOf(user));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
//        databaseHelper.deleteDatabase(getActivity());
        database = databaseHelper.getWritableDatabase();
        View view = inflater.inflate(R.layout.fragment_blank2, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {

            TenLoaiDanhMuc = bundle.getString("TenLoaiDoanhMuc");
            user = bundle.getString("user");
            thang = Integer.parseInt(bundle.getString("thang"));
            MaLoaiGiaoDich =  Integer.parseInt(bundle.getString("maLoaiGiaoDich"));
        }
        recyclerViewHangMucGiaoDichShowThongKe = view.findViewById(R.id.recyclerViewHangMucGiaoDichShowThongKe);

        // Tạo danh sách các mục chính (loại hạng mục) và các mục con (hạng mục)
        List<ClassHangMuc> HangMucList = createLoaiHangMucList();

        // Khởi tạo adapter cho RecyclerView chính (loại hạng mục)
        setAdapter = new CustomAdapter_Show_HangMucGiaoDich(HangMucList);

        // Thiết lập adapter cho RecyclerView chính (loại hạng mục)
        recyclerViewHangMucGiaoDichShowThongKe.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewHangMucGiaoDichShowThongKe.setAdapter(setAdapter);

        return view;
    }

    private List<ClassHangMuc> createLoaiHangMucList() {
        List<ClassHangMuc> HangMucList = new ArrayList<>();

        // Tạo các mục chính (loại hạng mục) và thêm các mục con (hạng mục) tương ứng
        List<ClassGiaoDich> getAllHangMuc = new ArrayList<>();
        getAllHangMuc.add(new ClassGiaoDich(TenLoaiDanhMuc));
        for (ClassGiaoDich giaoDich : getAllHangMuc) {
            BigDecimal so = giaoDich.getSotien();
            List<ClassGiaoDich> GiaoDichList = getAllHangMucDetail(TenLoaiDanhMuc, user, thang);

            // Calculate the total amount for each parent item
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (ClassGiaoDich childGiaoDich : GiaoDichList) {
                totalAmount = totalAmount.add(childGiaoDich.getSotien());
            }

            ClassHangMuc HangMuc = new ClassHangMuc(TenLoaiDanhMuc, totalAmount);
            HangMuc.setGiaoDichList(GiaoDichList);
            HangMucList.add(HangMuc);
        }

        return HangMucList;
    }
    public List<ClassGiaoDich> getAllHangMucDetail(String TenHangMuc, String user, int thang) {
        String month = (thang < 10) ? "0" + thang : String.valueOf(thang);

        String selectQuery = "select gd.DienGiai, gd.ngaygiaodich, gd.sotien from GiaoDich gd, HangMuc hm, LoaiHangMuc lhm, LoaiGiaoDich lgd , Account ac, vi vi " +
                "where hm.TenHangMuc= '" + TenHangMuc + "'  and ac.UserName = '" + user + "' and strftime('%m', ngaygiaodich) = '" + month + "'" +
                " and gd.ViGiaoDich = vi.MaVi and vi.AccountID = ac.AccountID and gd.MaHangMuc = hm.MaHangMuc " +
                "and hm.LoaiHangMuc = lhm.MaLoaiHangMuc and lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich";

        // Thực hiện truy vấn và lấy con trỏ (Cursor) chứa kết quả
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Tạo danh sách cục bộ để lưu trữ kết quả
        List<ClassGiaoDich> loaiHangMucList = new ArrayList<>();

// Di chuyển con trỏ đến vị trí đầu tiên
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String dienGiai = cursor.getString(cursor.getColumnIndex("DienGiai"));
                @SuppressLint("Range") double soTienDouble = cursor.getDouble(cursor.getColumnIndex("Sotien"));
                BigDecimal soTien = BigDecimal.valueOf(soTienDouble);
                @SuppressLint("Range") String dateString = cursor.getString(cursor.getColumnIndex("NgayGiaoDich"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date ngaygd = null;

                try {
                    ngaygd = dateFormat.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Tạo đối tượng ClassGiaoDich và thêm vào danh sách cục bộ
                loaiHangMucList.add(new ClassGiaoDich(ngaygd, dienGiai, soTien));
            } while (cursor.moveToNext());
        }

// Đóng con trỏ và cơ sở dữ liệu
        cursor.close();

// Trả về danh sách kết quả cho hạng mục cụ thể
        return loaiHangMucList;
    }


}