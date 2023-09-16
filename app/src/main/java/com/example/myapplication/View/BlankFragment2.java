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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment2 extends Fragment {

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
    String user ;
    RecyclerView recyclerViewHangMucGiaoDichShowThongKe;
CustomAdapter_Show_HangMucGiaoDich setAdapter;

    public BlankFragment2() {
    }
     public static BlankFragment2 newInstance(int param1, String TenLoaiDanhMuc, int maLoaiGiaoDich, String user) {
        BlankFragment2 fragment = new BlankFragment2();
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
        List<ClassGiaoDich> getAllHangMuc = getAllHangMucNames(TenLoaiDanhMuc, user, thang, MaLoaiGiaoDich);

        // Create a map to store the total amount for each category
        Map<String, BigDecimal> totalAmountMap = new HashMap<>();

        for (ClassGiaoDich giaoDich : getAllHangMuc) {
            String tenHangMuc = giaoDich.getTenHangMuc();
            BigDecimal so = giaoDich.getSotien();

            // Check if the category name already exists in the map
            if (totalAmountMap.containsKey(tenHangMuc)) {
                // If it exists, add the amount to the existing total
                BigDecimal currentTotal = totalAmountMap.get(tenHangMuc);
                BigDecimal newTotal = currentTotal.add(so);
                totalAmountMap.put(tenHangMuc, newTotal);
            } else {
                // If it does not exist, add a new entry to the map
                totalAmountMap.put(tenHangMuc, so);
            }
        }

        // Create new HangMucList with the aggregated data
        for (Map.Entry<String, BigDecimal> entry : totalAmountMap.entrySet()) {
            String tenHangMuc = entry.getKey();
            BigDecimal so = entry.getValue();

            ClassHangMuc HangMuc = new ClassHangMuc(tenHangMuc, so);
            List<ClassGiaoDich> GiaoDichList = getAllHangMucDetail(tenHangMuc, user, thang);
            HangMuc.setGiaoDichList(GiaoDichList);
            HangMucList.add(HangMuc);
        }

        // Sort the HangMucList by date in ascending order
        Collections.sort(HangMucList, new Comparator<ClassHangMuc>() {
            @Override
            public int compare(ClassHangMuc hm1, ClassHangMuc hm2) {
                List<ClassGiaoDich> list1 = hm1.getGiaoDichList();
                List<ClassGiaoDich> list2 = hm2.getGiaoDichList();

                if (list1.isEmpty() || list2.isEmpty()) {
                    return 0;
                }

                Date date1 = list1.get(0).getNgayGiaoDich();
                Date date2 = list2.get(0).getNgayGiaoDich();
                return date1.compareTo(date2);
            }
        });

        return HangMucList;
    }






    public List<ClassGiaoDich> getAllHangMucNames(String TenLoaiDanhMuc, String user, int thang, int MaLoaiGiaoDich) {
        List<ClassGiaoDich> loaiHangMucList = new ArrayList<>();
        // Câu lệnh SELECT để lấy tên các hạng mục
        String month = (thang < 10) ? "0" + thang : String.valueOf(thang);

        String selectQuery = "select Distinct(hm.TenHangMuc), gd.sotien from GiaoDich gd, HangMuc hm, LoaiHangMuc lhm, LoaiGiaoDich lgd , Account ac, vi vi " +
                "where lhm.TenLoaiHangMuc= '" + TenLoaiDanhMuc + "'  and ac.UserName = '" + user + "' and strftime('%m', ngaygiaodich) = '" + month + "'" +
                "and lgd.MaLoaiGiaoDich = '"+MaLoaiGiaoDich+"' and gd.ViGiaoDich = vi.MaVi and vi.AccountID = ac.AccountID and gd.MaHangMuc = hm.MaHangMuc " +
                "and hm.LoaiHangMuc = lhm.MaLoaiHangMuc and lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich";

        // Thực hiện truy vấn và lấy con trỏ (Cursor) chứa kết quả
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Di chuyển con trỏ đến vị trí đầu tiên
        if (cursor.moveToFirst()) {
            do {
                // Lấy tên loại hạng mục từ cột TenLoaiHangMuc
                @SuppressLint("Range") String tenHangMuc = cursor.getString(cursor.getColumnIndex("TenHangMuc"));
                @SuppressLint("Range") double soTienDouble = cursor.getDouble(cursor.getColumnIndex("Sotien"));
                BigDecimal soTien = BigDecimal.valueOf(soTienDouble);

                // Tạo đối tượng ClassGiaoDich và thêm vào danh sách
                loaiHangMucList.add(new ClassGiaoDich(soTien, tenHangMuc));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return loaiHangMucList;
    }

    public List<ClassGiaoDich> getAllHangMucDetail(String TenHangMuc, String user, int thang) {
        List<ClassGiaoDich> loaiHangMucList = new ArrayList<>();
        String month = (thang < 10) ? "0" + thang : String.valueOf(thang);

        String selectQuery = "select gd.DienGiai, gd.ngaygiaodich, gd.sotien from GiaoDich gd, HangMuc hm, LoaiHangMuc lhm, LoaiGiaoDich lgd , Account ac, vi vi " +
                "where hm.TenHangMuc= '" + TenHangMuc + "'  and ac.UserName = '" + user + "' and strftime('%m', ngaygiaodich) = '" + month + "'" +
                " and gd.ViGiaoDich = vi.MaVi and vi.AccountID = ac.AccountID and gd.MaHangMuc = hm.MaHangMuc " +
                "and hm.LoaiHangMuc = lhm.MaLoaiHangMuc and lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich";

        // Thực hiện truy vấn và lấy con trỏ (Cursor) chứa kết quả
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Di chuyển con trỏ đến vị trí đầu tiên
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String dienGiai = cursor.getString(cursor.getColumnIndex("DienGiai"));
                @SuppressLint("Range") String dateString = cursor.getString(cursor.getColumnIndex("NgayGiaoDich"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date ngaygd = null;

                try {
                    ngaygd = dateFormat.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                @SuppressLint("Range") double soTienDouble = cursor.getDouble(cursor.getColumnIndex("Sotien"));
                BigDecimal soTien = BigDecimal.valueOf(soTienDouble);
                SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");
                String ngayGiaoDichFormatted = displayFormat.format(ngaygd);

                // Tạo đối tượng ClassGiaoDich và thêm vào danh sách
                loaiHangMucList.add(new ClassGiaoDich(ngaygd,dienGiai,soTien));
            } while (cursor.moveToNext());
        }

        // Đóng con trỏ và cơ sở dữ liệu
        cursor.close();

        return loaiHangMucList;
    }


}