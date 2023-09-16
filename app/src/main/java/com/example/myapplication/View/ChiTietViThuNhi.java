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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.Adapter.CustomRecyclerViewAdapter;
import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.Model.ChildItem;
import com.example.myapplication.Model.ClassGiaoDich;
import com.example.myapplication.R;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChiTietViThuNhi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChiTietViThuNhi extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SQLiteDatabase database;
    TextView txtTongThu,txtTongChi,txtTienConLai;
RecyclerView reChiTietGiaoDichTrongVi;
String tenVi;
String user;

Spinner spinnerDuLieu;
    private CustomRecyclerViewAdapter adapter;
    private List<Object> itemList;
    public ChiTietViThuNhi() {
        // Required empty public constructor
    }


    public static ChiTietViThuNhi newInstance( String tenVi,String user) {
        ChiTietViThuNhi fragment = new ChiTietViThuNhi();
        Bundle args = new Bundle();
        args.putString("user", user);
        args.putString("tenVi", tenVi);
        fragment.setArguments(args);
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chi_tiet_vi_thu_nhi, container, false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
//        databaseHelper.deleteDatabase(getActivity());
        database = databaseHelper.getWritableDatabase();
        Bundle bundle = getArguments();
        if (bundle != null) {
            user = bundle.getString("user");
            tenVi = bundle.getString("tenVi");
        }
        addControl(view);
        addEvent();



        // Khởi tạo adapter cho RecyclerView chính (loại hạng mục)
        List<ClassGiaoDich> loaiHangMucList = createLoaiHangMucList(1);
        adapter = new CustomRecyclerViewAdapter(loaiHangMucList);

        reChiTietGiaoDichTrongVi.setLayoutManager(new LinearLayoutManager(getActivity()));
        reChiTietGiaoDichTrongVi.setAdapter(adapter);


        List<String> data = new ArrayList<>();
        data.add("30 ngày gần nhất");
        data.add("Tháng này");
        data.add("Năm này");
        data.add("Toàn bộ thời gian");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, data);

        // Set the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attach the adapter to the Spinner
        spinnerDuLieu.setAdapter(spinnerAdapter);
        return view;
    }
    private void addEvent(){
        BigDecimal thuTien = GetTien(user,tenVi, 1);
        BigDecimal chiTien = GetTien(user,tenVi, 2);
        BigDecimal tienConLai = GetTienTrongVi(user,tenVi);
        txtTongChi.setText(thuTien.toString());
        txtTongThu.setText(chiTien.toString());
        txtTienConLai.setText(tienConLai.toString());
        spinnerDuLieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                List<ClassGiaoDich> loaiHangMucList = null;
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (selectedItem.equals("30 ngày gần nhất")){
                    loaiHangMucList = createLoaiHangMucList(1);
                } else if (selectedItem.equals("Tháng này")) {
                   loaiHangMucList = createLoaiHangMucList(2);
                }
                else if (selectedItem.equals("Năm này")) {
                   loaiHangMucList = createLoaiHangMucList(3);
                }
                else if (selectedItem.equals("Toàn bộ thời gian")){
                    loaiHangMucList = createLoaiHangMucList(4);
                }
                adapter = new CustomRecyclerViewAdapter(loaiHangMucList);
                reChiTietGiaoDichTrongVi.setLayoutManager(new LinearLayoutManager(getActivity()));
                reChiTietGiaoDichTrongVi.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do something when nothing is selected
            }
        });


    }


    private List<ClassGiaoDich> createLoaiHangMucList(int id) {

            List<ClassGiaoDich> giaoDichList = new ArrayList<>();
            List<String> getAllGiaoDichList = null;
            if (id ==1) {
             getAllGiaoDichList = getDatesWithinLast30Days();
            } else if (id==2) {
                int thang = getCurrentMonth();
                getAllGiaoDichList = getMonth(thang);
            } else if (id==3) {
                int nam = getCurrentYear();
                getAllGiaoDichList = getYear(nam);

            }
            else if (id==4) {

                getAllGiaoDichList = getAll();

            }


        for (int i = 0; i < getAllGiaoDichList.size(); i++) {
                String ngay = getAllGiaoDichList.get(i);
                List<ChildItem> getAllHangMuc = getAllHangMucDetail(user, tenVi, ngay);


                ClassGiaoDich LoaiHangMuc = new ClassGiaoDich(ngay);
                List<ChildItem> HangMucList = new ArrayList<>();
                for (ChildItem childItem : getAllHangMuc) {
                    String TenHM = childItem.getName();
                    BigDecimal soTien = childItem.getAmount();
                    HangMucList.add(new ChildItem(TenHM, soTien));
                }
                LoaiHangMuc.setChildItemList(HangMucList);
                giaoDichList.add(LoaiHangMuc);

            }

        return giaoDichList;
    }
    public List<ChildItem> getAllHangMucDetail( String user, String tenVi,String ngay) {
        //để lấy tên hạng mục với số tiền
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        database = databaseHelper.getWritableDatabase();
        String selectQuery = "select hm.TenHangMuc, Sotien from GiaoDich gd, HangMuc hm, LoaiHangMuc lhm, LoaiGiaoDich lgd , Account ac, vi vi where ac.UserName = '"+user+"' and vi.TenVi= '"+tenVi+"'" +
                "and ngaygiaodich = '"+ngay+"' and gd.ViGiaoDich = vi.MaVi and vi.AccountID = ac.AccountID and gd.MaHangMuc = hm.MaHangMuc and hm.LoaiHangMuc = lhm.MaLoaiHangMuc and lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich";

        Cursor cursor = database.rawQuery(selectQuery, null);

        List<ChildItem> childItems = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String Ten = cursor.getString(cursor.getColumnIndex("TenHangMuc"));
                @SuppressLint("Range") double soTienDouble = cursor.getDouble(cursor.getColumnIndex("Sotien"));
                BigDecimal soTien = BigDecimal.valueOf(soTienDouble);

                childItems.add(new ChildItem(Ten, soTien));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return childItems;
    }

    public List<String> getDatesWithinLast30Days() {

        List<String> dateList = new ArrayList<>();

        // Get today's date
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        // Calculate the date 30 days ago
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        Date thirtyDaysAgo = calendar.getTime();

        // Convert dates to string format "yyyy-MM-dd"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        // Select distinct dates from the database that fall within the last 30 days, sorted by date in descending order
        String selectQuery = "SELECT DISTINCT gd.ngaygiaodich FROM  GiaoDich gd, HangMuc hm, LoaiHangMuc lhm, LoaiGiaoDich lgd , Account ac, vi vi WHERE ac.UserName = '"+user+"' and NgayGiaoDich BETWEEN strftime('%Y-%m-%d', 'now', '-30 days') " +
                "AND strftime('%Y-%m-%d', 'now') ORDER BY NgayGiaoDich DESC";

        // Perform the query
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Loop through the cursor and add the dates to the dateList
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(0);
                dateList.add(date);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();

        return dateList;
    }
    public List<String> getYear(int nam) {
        List<String> dateList = new ArrayList<>();

        // Select distinct dates from the database that fall within the last 30 days, sorted by date in descending order
        String selectQuery = "SELECT DISTINCT NgayGiaoDich FROM GiaoDich WHERE strftime('%Y', NgayGiaoDich) = '"+nam+"' ORDER BY NgayGiaoDich DESC";

        // Perform the query
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Loop through the cursor and add the dates to the dateList
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(0);
                dateList.add(date);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        database.close();

        return dateList;
    }
    public List<String> getMonth(int thang) {
        List<String> dateList = new ArrayList<>();

        String month = (thang < 10) ? "0" + thang : String.valueOf(thang);

        // Select distinct dates from the database that fall within the last 30 days, sorted by date in descending order
        String selectQuery = "SELECT DISTINCT NgayGiaoDich FROM GiaoDich WHERE strftime('%m', NgayGiaoDich) = '"+month+"' ORDER BY NgayGiaoDich DESC";

        // Perform the query
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Loop through the cursor and add the dates to the dateList
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(0);
                dateList.add(date);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        database.close();

        return dateList;
    }
    public List<String> getAll() {
        List<String> dateList = new ArrayList<>();


        // Select distinct dates from the database that fall within the last 30 days, sorted by date in descending order
        String selectQuery = "SELECT DISTINCT NgayGiaoDich FROM GiaoDich ORDER BY NgayGiaoDich DESC";

        // Perform the query
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Loop through the cursor and add the dates to the dateList
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(0);
                dateList.add(date);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        database.close();

        return dateList;
    }
    public int getCurrentMonth() {
        // Get the current calendar instance
        Calendar calendar = Calendar.getInstance();

        // Get the current month (Note: Calendar.MONTH is zero-based, so January is represented by 0)
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

        return currentMonth;
    }
    public int getCurrentYear() {
        // Get the current calendar instance
        Calendar calendar = Calendar.getInstance();

        // Get the current year
        int currentYear = calendar.get(Calendar.YEAR);

        return currentYear;
    }
    private BigDecimal GetTienTrongVi(String user, String tenVi) {
        BigDecimal tienTrongVi = BigDecimal.ZERO;
        String neNum = "SELECT TienTrongVi FROM GiaoDich gd, HangMuc hm, LoaiHangMuc lhm ,LoaiGiaoDich lgd, account ac, vi vi" +
                " WHERE  ac.UserName = '"+user+"' and vi.TenVi= '"+tenVi+"' and gd.MaHangMuc = hm.MaHangMuc and hm.LoaiHangMuc = lhm.MaLoaiHangMuc" +
                " and gd.ViGiaoDich = vi.MaVi  and  lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich and vi.AccountID = ac.AccountID";


        Cursor cursor = database.rawQuery(neNum, null);
        if (cursor.moveToFirst()) {
            double sum = cursor.getDouble(0);
            tienTrongVi = BigDecimal.valueOf(sum);
            tienTrongVi.negate();
        }
        cursor.close();
        return tienTrongVi;
    }

    private BigDecimal GetTien(String user, String tenVi,int MaLoaiGiaoDich) {
        BigDecimal chiTien = BigDecimal.ZERO;
        String neNum = "SELECT sum(sotien) FROM GiaoDich gd, HangMuc hm, LoaiHangMuc lhm ,LoaiGiaoDich lgd, account ac, vi vi" +
                " WHERE lgd.MaLoaiGiaoDich ='"+MaLoaiGiaoDich+"' and ac.UserName = '"+user+"' and vi.TenVi= '"+tenVi+"' and gd.MaHangMuc = hm.MaHangMuc and hm.LoaiHangMuc = lhm.MaLoaiHangMuc" +
                " AND gd.NgayGiaoDich >= date('now', '-30 days') and gd.ViGiaoDich = vi.MaVi  and  lgd.MaLoaiGiaoDich = lhm.MaLoaiGiaoDich and vi.AccountID = ac.AccountID";


        Cursor cursor = database.rawQuery(neNum, null);
        if (cursor.moveToFirst()) {
            double sum = cursor.getDouble(0);
            chiTien = BigDecimal.valueOf(sum);
            chiTien.negate();
        }
        cursor.close();
        return chiTien;
    }
    private void addControl(View view){
        txtTongChi = view.findViewById(R.id.txtTongChi);
        txtTongThu = view.findViewById(R.id.txtTongThu);
        txtTienConLai = view.findViewById(R.id.txtTienConLai);
        reChiTietGiaoDichTrongVi = view.findViewById(R.id.reChiTietGiaoDichTrongVi);
        spinnerDuLieu = view.findViewById(R.id.spinnerDuLieu);


    }

    @Override
    public void onDestroy() {
        // Close the database when the Fragment is destroyed
        database.close();
        super.onDestroy();
    }

}